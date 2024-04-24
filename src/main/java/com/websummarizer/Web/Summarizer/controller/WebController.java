package com.websummarizer.Web.Summarizer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.websummarizer.Web.Summarizer.llmConnectors.Bart;
import com.websummarizer.Web.Summarizer.llmConnectors.Llm;
import com.websummarizer.Web.Summarizer.llmConnectors.OpenAi;
import com.websummarizer.Web.Summarizer.model.History;
import com.websummarizer.Web.Summarizer.model.LoginResponseDTO;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.model.UserDTO;
import com.websummarizer.Web.Summarizer.model.history.HistoryResAto;
import com.websummarizer.Web.Summarizer.model.user.UserReqAto;
import com.websummarizer.Web.Summarizer.parsers.HTMLParser;
import com.websummarizer.Web.Summarizer.services.UserServiceImpl;
import com.websummarizer.Web.Summarizer.services.history.HistoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Controller for web-related actions.
 */
@Controller
public class WebController {

    @Autowired
    private final Bart bart;
    @Autowired
    private final OpenAi openAi;
    @Autowired
    private AuthenticationController authenticationController;
    @Autowired
    ShortLinkGenerator shortLinkGenerator;
    @Autowired
    BitLyController bitLyController;
    @Autowired
    HistoryService historyService;
    @Autowired
    UserServiceImpl userService;
    @Value("${WEBADDRESS}")
    private String webAddress;

    private Llm currentLlm;
    private boolean flag = true;
    private long hid = -1;
    private String shortUrl;

    private static final Logger logger = Logger.getLogger(WebController.class.getName());

    /**
     * Constructor for WebController.
     *
     * @param bart The Bart instance to use.
     */
    public WebController(Bart bart, OpenAi openAi) {
        this.bart = bart;
        this.currentLlm = bart; //default llm as bart
        this.openAi = openAi;
    }

    private ResponseEntity<String> createPostRequestForHistory(HttpSession session,
                                                               String isLoggedIn,
                                                               String historyContent,
                                                               String httpUrl) {
        //todo check the logic if this is correct
        String jwt = (String) session.getAttribute("jwt");
        String email = (String) session.getAttribute("email");
        ResponseEntity<String> response = null;
        if (isLoggedIn.equals("true")) { // if the user is logged in and it is the first summary
            try {
                logger.info("user is logged making a post request");
                //Generate a new link for the history
                RestTemplate restTemplate = new RestTemplate();
                // Create headers
                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(jwt);
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

                // Create the request body as form data
                MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                map.add("output", historyContent);
                map.add("email", email);

                // Create an entity which includes the headers and the body
                HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);

                // Make the request
                response = restTemplate.exchange(
                        httpUrl,
                        HttpMethod.POST,
                        requestEntity,
                        String.class
                );
            }catch (Exception e){
                return null; // todo
            }
        }
        return response;
    }

    private ResponseEntity<String> createPostRequestForFetchHistory(HttpSession session,
                                                               String isLoggedIn) {
        //todo check the logic if this is correct
        String jwt = (String) session.getAttribute("jwt");
        String email = (String) session.getAttribute("email");
        ResponseEntity<String> response = null;
        if (isLoggedIn.equals("true")) { // if the user is logged in and it is the first summary
            try {
                logger.info("user is logged making a post request to fetch all histories");
                //Generate a new link for the history
                RestTemplate restTemplate = new RestTemplate();
                // Create headers
                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(jwt);
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

                // Create the request body as form data
                MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                map.add("email", email);

                // Create an entity which includes the headers and the body
                HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);

                // Make the request
                response = restTemplate.exchange(
                        webAddress+"/users/histories",
                        HttpMethod.POST,
                        requestEntity,
                        String.class
                );
            }catch (Exception e){
                return null; //todo
            }
        }
        return response;
    }
    private void extractHistoryData1(ResponseEntity<String> response){
        logger.info("new history response body: " + response.getBody());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = mapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            //todo
            logger.severe("error creating json object of history");
        }
        assert rootNode != null;
        int id = rootNode.get("id").asInt();
        hid = id;
        shortUrl = rootNode.get("shortLink").asText();
        flag = false;
        logger.info("extracted history id: " + id);
    }
    private void extractHistoryData2(ResponseEntity<String> response) {
        logger.info("new history append body: " + response.getBody());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = mapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            //todo
            logger.severe("error creating json object of history");
        }

        assert rootNode != null;
        int id = rootNode.get("id").asInt();
        hid = id;
        logger.info("extracted history id: " + id);
    }
    public List<History> extractAllHistories(ResponseEntity<String> response) {
        logger.info("Extracting the histories from response histories: " + response.getBody());

        List<History> histories = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootNode = mapper.readTree(response.getBody());
            for (JsonNode node : rootNode) {
                History history = new History();
                history.setHistoryContent(node.get("historyContent").asText());
                history.setShortLink(node.get("shortLink").asText());
                histories.add(history);
            }
            for(History history:histories){
                logger.severe("histories are: "+history);
            }
        } catch (JsonProcessingException e) {
            logger.severe("Error creating JSON object while fetching histories: " + e.getMessage());
            // Handle exception
        }

        return histories;
    }

    /**
     * Endpoint for getting a summary.
     *
     * @param input The input from the user.
     * @param model The model to use.
     * @return The name of the view to render.
     */
    @PostMapping("/api/summary")
    public String getSummary(
            @RequestParam(value = "isLoggedIn", required = false) String isLoggedIn,
            @RequestParam(value = "isProUser", required = false) String isProUser,
            @RequestParam(value = "input") String input,
            HttpServletRequest request,
            HttpSession session,
            Model model
    ) {
        boolean isValidOutput = true;
        logger.info("flag " + flag + " " + hid);
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd h:mm:ss a");

        String username = (String) request.getSession().getAttribute("first_name");
        String output;   // This stores the summarized web content
        String url = null;      // This stores the shortened URL
        String link;     // This stores the short link code

        input = input.trim();
        boolean isURL = isValidURL(input);

        if ((username == null) || (username.equals("undefined")) || (!isLoggedIn.equals("true"))) {
            username = "You";
        }

        if (isURL) {
            logger.info("got the URL:" + input);
            try {
                output = currentLlm.queryModel(HTMLParser.parser(input));
            } catch (IOException e) {
                output = "Error Occurred. Please try again.";
                isValidOutput = false;
            }
        } else {
            logger.info("got the text:" + input);
            try {
                output = currentLlm.queryModel(input);
            } catch (Exception e) {
                output = "Error Occurred while fetching your results. Please try again.";
                isValidOutput = false;
            }
        }

        // if the user is logged in and it is the first summary
        if (isLoggedIn.equals("true") && flag) {
            logger.info("user is logged in appending history now:");
            String httpUrl = webAddress + "users/add-new-history";
            // Make the request only if output is valid
            if(isValidOutput) {
                ResponseEntity<String> response = createPostRequestForHistory(session, isLoggedIn, output, httpUrl);
                if (response != null && response.getStatusCode().is2xxSuccessful()) {
                    extractHistoryData1(response);
                }
                else {
                     output = "Failed to process request please try again";
                }
            }
        }

        // if the user is logged in, and it is not the first summary so add to previous
        else if (isLoggedIn.equals("true") && !flag) {
            logger.info("user is logged in appending history now:");
            String httpUrl = webAddress + "/users/" + shortUrl + "/append-history";
            // Make the request only if output is valid
            if(isValidOutput) {
                ResponseEntity<String> response = createPostRequestForHistory(session, isLoggedIn, output, httpUrl);
                if (response != null && response.getStatusCode().is2xxSuccessful()) {
                    extractHistoryData2(response);
                }
            }

        }

        else {
            logger.info("user is not logged in saving the history in temp variable to avoid loss:");
            //save the content in a temporary history object //todo
        }
        //link = shortlink.ShortLinkGenerator(input, output, session);
        //url = webAddress + link;

        model.addAttribute("date", dateFormat.format(date));
        model.addAttribute("user", username);
        model.addAttribute("input", input);
        model.addAttribute("output", output);

        // Share Button Attributes
        model.addAttribute("fb", "https://www.addtoany.com/add_to/facebook?linkurl=" + url);
        model.addAttribute("twitter", "https://www.addtoany.com/add_to/x?linkurl=" + url);
        model.addAttribute("email", "https://www.addtoany.com/add_to/email?linkurl=" + url);
        model.addAttribute("link", url);

        return "api/summary";
    }

    @PostMapping("/api/newchat")
    public String newChat() {
        this.flag = true;
        this.hid = -1;
        logger.info("flag "+ flag + " "+ hid);
        return "api/summary";
    }

    /**
     * Endpoint for user sign in.
     *
     * @return The name of the view to render.
     */
    @PostMapping("/user/login")
    public String login(
            @RequestParam(value = "isLoggedIn", required = false) String isLoggedIn,
            @RequestParam(value = "isProUser", required = false) String isProUser,
            @RequestParam(value = "path", required = false) String path,
            HttpServletRequest request,
            Model model
    ) {
        if ((isLoggedIn != null) && (isLoggedIn.equals("true"))) {
            if ((path != null) && (path.equals("pro"))) {
                if ((isProUser != null) && (isProUser.equals("true"))) {
                    model.addAttribute("isProUser", true);

                    return "user/thankyou";
                } else {
                    return "user/pro";
                }
            } else {
                User user = userService.getFoundUser((String)request.getSession().getAttribute("username"));
                List<HistoryResAto> histories = historyService.findHistoryId(user.getId());

                logger.info("list of histories:");
                for(HistoryResAto historyResAto : histories){
                    logger.info("historyResAto"+historyResAto);
                }

                model.addAttribute("histories", histories);
                model.addAttribute("llm", request.getSession().getAttribute("llm"));
                model.addAttribute("email", request.getSession().getAttribute("username"));
                model.addAttribute("isLoggedIn", true);
                model.addAttribute("isProUser", isProUser);

                return "user/account";
            }
        } else {
            return "user/login";
        }
    }

    /**
     * Endpoint for validating the login of a user.
     */
    @PostMapping("/user/auth")
    public String authUser(
            @RequestParam(value = "login_email") String email,
            @RequestParam(value = "login_password") String password,
            @RequestParam(value = "isProUser", required = false) String isProUser,
            @RequestParam(value = "path", required = false) String path,
            @ModelAttribute UserDTO userDTO,
            HttpServletRequest request,
            HttpSession session,
            Model model
    ) {
        ResponseEntity<?> loginResponse = authenticationController.loginUser(userDTO);

        boolean isValidLogin = loginResponse.getStatusCode().is2xxSuccessful();

        LoginResponseDTO loginResponseDTO = (LoginResponseDTO) loginResponse.getBody();
        assert loginResponseDTO != null;
        logger.info("jwt:" + loginResponseDTO.getJwt());


        session.setAttribute("jwt", loginResponseDTO.getJwt());
        session.setAttribute("email", loginResponseDTO.getUser().getEmail());


        if (isValidLogin) {
            request.getSession().setAttribute("username", userDTO.getLogin_email());
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("isValid", true);
            model.addAttribute("html", "<span class=\"bi bi-check-circle-fill\"></span>");
            model.addAttribute("message", "User '" + email + "' logged in successfully.");

            if ((path != null) && (path.equals("pro"))) {
                if ((isProUser != null) && (isProUser.equals("true"))) {
                    model.addAttribute("isProUser", true);

                    return "user/thankyou";
                } else {
                    return "user/pro";
                }
            } else {
                model.addAttribute("email", email);
                model.addAttribute("isProUser", isProUser);

                return "user/account";
            }
        } else {
            model.addAttribute("isValid", false);
            model.addAttribute("html", "<span class=\"bi bi-exclamation-triangle-fill\"></span>");
            model.addAttribute("message", "Login auth error for '" + email + "'. Please try again.");

            return "user/login";
        }
    }

    /**
     * Endpoint for user account settings.
     *
     * @return The name of the view to render.
     */
    @PostMapping("/user/account")
    public String account(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "isLoggedIn") String isLoggedIn,
            @RequestParam(value = "isProUser", required = false) String isProUser,
            @ModelAttribute UserReqAto user,
            Model model
    ) {
        logger.info("User update request for the following user: " + user);

        ResponseEntity<?> isValidUpdate = authenticationController.updateUser(user);
        if (user != null) { //todo
            if (Objects.equals(user.getAccount_llm(), "bart")) {
                logger.info("llm selected : bart");
                this.currentLlm = bart;
            } else if (Objects.equals(user.getAccount_llm(), "openai")) {
                logger.info("llm selected : openai");
                this.currentLlm = openAi;
            }
        }
        model.addAttribute("email", email);
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("isProUser", isProUser);

        if (isValidUpdate.getStatusCode().isSameCodeAs(HttpStatus.OK)) {
            model.addAttribute("isValid", true);
            model.addAttribute("html", "<span class=\"bi bi-check-circle-fill\"></span>");
            model.addAttribute("message", "Account settings for '" + email + "' have been updated.");
        } else if (isValidUpdate.getStatusCode().isSameCodeAs(HttpStatus.FORBIDDEN)) {
            model.addAttribute("isValid", false);
            model.addAttribute("html", "<span class=\"bi bi-exclamation-triangle-fill\"></span>");
            model.addAttribute("message", "Account settings for '" + email + "' cannot be updated as it is a google/github account");
        } else {
            model.addAttribute("isValid", false);
            model.addAttribute("html", "<span class=\"bi bi-exclamation-triangle-fill\"></span>");
            model.addAttribute("message", "Failed to save settings for '" + email + "'. Please try again.");
        }
        return "user/account";
    }

    /**
     * Endpoint for user registration.
     *
     * @return The name of the view to render.
     */
    @PostMapping("/user/register")
    public String register(
            @RequestParam(value = "login_email") String email,
            Model model
    ) {
        model.addAttribute("email", email);

        return "user/register";
    }

    /**
     * Endpoint for creating a user.
     *
     * @param user The user to create.
     */
    @PostMapping("/user/create")
    public String createUser(
            @RequestParam(value = "first_name") String first_name,
            @RequestParam(value = "email") String email,
            @ModelAttribute User user,
            HttpServletRequest request,
            Model model
    ) {
        logger.info("Received user creation request: " + user);
        boolean isRegistered = false;

        //check password /todo put back
//        if (!shortLinkGenerator.checkPassword(user.getPassword())) {
//            model.addAttribute("isValid", false);
//            model.addAttribute("html", "<span class=\"bi bi-exclamation-triangle-fill\"></span>");
//            model.addAttribute("message", "Password must contain at least 8 characters, 1 uppercase letter, 1 lowercase letter, 1 number, and 1 special character");
//
//            return "user/register";
//        }

        try {
            user.setLlmSelection("bart");//added default llm as bart while registration
            ResponseEntity<?> registerResponse = authenticationController.registerUser(user);
            isRegistered = registerResponse.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            logger.warning("User creation failed: " + e.getMessage());
        }

        if (isRegistered) {
            request.getSession().setAttribute("first_name", first_name);
            model.addAttribute("email", email);
            model.addAttribute("isValid", true);
            model.addAttribute("html", "<span class=\"bi bi-check-circle-fill\"></span>");
            model.addAttribute("message", "User '" + email + "' created successfully. Please login.");

            return "user/login";
        } else {
            model.addAttribute("isValid", false);
            model.addAttribute("html", "<span class=\"bi bi-exclamation-triangle-fill\"></span>");
            model.addAttribute("message", "Registration error for '" + email + "'. Please try again.");

            return "user/register";
        }
    }

    @PostMapping("/user/pro")
    public String pro(
            @RequestParam(value = "isLoggedIn", required = false) String isLoggedIn,
            @RequestParam(value = "isProUser", required = false) String isProUser,
            Model model
    ) {
        if ((isLoggedIn != null) && (isLoggedIn.equals("true"))) {
            if ((isProUser != null) && (isProUser.equals("true"))) {
                model.addAttribute("isProUser", true);

                return "user/thankyou";
            } else {
                return "user/pro";
            }
        } else {
            model.addAttribute("isValid", false);
            model.addAttribute("html", "<span class=\"bi bi-exclamation-triangle-fill\"></span>");
            model.addAttribute("message", "Please login to unlock or purchase pro features.");

            return "user/login";
        }
    }

    /**
     * Endpoint for purchasing pro features.
     *
     * @return The name of the view to render.
     */
    @PostMapping("/user/purchase")
    public String purchase(
            @RequestParam(value = "isLoggedIn") String isLoggedIn,
            Model model
    ) {
        boolean isValidPurchase = true; // Not real payment, therefore always accept

        model.addAttribute("isLoggedIn", isLoggedIn);

        if (isValidPurchase) {
            model.addAttribute("isProUser", true);
            model.addAttribute("isValid", true);
            model.addAttribute("html", "<span class=\"bi bi-check-circle-fill\"></span>");
            model.addAttribute("message", "Payment successful. Thank you for your purchase.");

            return "user/thankyou";
        } else {
            model.addAttribute("isValid", false);
            model.addAttribute("html", "<span class=\"bi bi-exclamation-triangle-fill\"></span>");
            model.addAttribute("message", "Payment processing failed. Please try again.");

            return "user/pro";
        }
    }

    /**
     * Endpoint for cancelling membership.
     *
     * @return The name of the view to render.
     */
    @PostMapping("/user/cancel")
    public String cancel(
            Model model
    ) {
        model.addAttribute("isValid", true);
        model.addAttribute("html", "<span class=\"bi bi-check-circle-fill\"></span>");
        model.addAttribute("message", "Membership has been cancelled. You will no longer be billed.");

        return "user/cancel";
    }

    /**
     * Checks if a string is a valid URL.
     *
     * @param urlStr The string to check.
     * @return true if the string is a valid URL, falsef otherwise.
     */
    private static boolean isValidURL(String urlStr) {
        try {
            // Attempt to create a URL object
            new URL(urlStr).toURI();
            return true;
        } catch (Exception e) {
            // If an exception occurs, URL is not valid
            return false;
        }
    }
}
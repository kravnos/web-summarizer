package com.websummarizer.Web.Summarizer.llmConnectors;

/**
 * Interface for interacting with a Language Model (LLM).
 */
public interface Llm {

    /**
     * Query the language model with the given input text.
     *
     * @param inputText The input text to query the model.
     * @return The response from the language model.
     */
    String queryModel(String inputText);

}

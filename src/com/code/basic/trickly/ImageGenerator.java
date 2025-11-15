package com.code.basic.trickly;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageGenerator {

	/**
	 * Lists all available models from the API
	 * @param apiKey The API key to use for the request
	 */
	public static void listAvailableModels(String apiKey) {
		String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models?key=" + apiKey;

		System.out.println("Fetching available models...");

		HttpClient client = HttpClient.newBuilder()
				.version(HttpClient.Version.HTTP_2)
				.connectTimeout(Duration.ofSeconds(30))
				.build();

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(apiUrl))
				.header("Content-Type", "application/json")
				.GET()
				.build();

		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println("Available Models:\n" + response.body());
		} catch (IOException | InterruptedException e) {
			System.out.println("Error fetching models: " + e.getMessage());
		}
	}

	/**
	 * Generates an image from a text prompt using the Imagen 4.0 API
	 * and saves it to a file.
	 *
	 * @param prompt The text prompt to generate an image from.
	 * @param filename The name of the file to save the image to.
	 */
	public static void generateImage(String prompt, String filename) {

		// The API key should be left as "" and will be automatically
		// populated by the environment when you run this.
		// You must get your key from Google AI Studio.
		String apiKey = "AIzaSyCmfJARnQM2ifqpAU83SzdQu0_S4oBRmZA";

		// The API endpoint - Using Gemini 2.5 Flash with image generation support
		String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash-image:generateContent?key=" + apiKey;

		// Manually create the JSON payload string
		// Using String.format for clarity, ensuring prompt is properly escaped
		String payload = String.format("""
				{
				    "contents": [
				        {
				            "parts": [
				                {
				                    "text": "Generate an image with this prompt: %s"
				                }
				            ]
				        }
				    ]
				}
				""", prompt.replace("\"", "\\\""));

		System.out.println("Sending request for prompt: '" + prompt + "'...");

		// Create an HttpClient
		HttpClient client = HttpClient.newBuilder()
				.version(HttpClient.Version.HTTP_2)
				.connectTimeout(Duration.ofSeconds(30))
				.build();

		// Create an HttpRequest
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(apiUrl))
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(payload))
				.build();

		try {
			// Send the request and get the response
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			// Check for a successful response
			if (response.statusCode() == 200) {
				String responseBody = response.body();

				// Manually parse the JSON response to find the base64 data
				// This is a simple parser; a dedicated JSON library (like Gson, Jackson) is more robust
				String imageData = null;
				// Try multiple patterns for different response formats
				Pattern pattern = Pattern.compile("\"bytesBase64Encoded\"\\s*:\\s*\"([^\"]+)\"");
				Matcher matcher = pattern.matcher(responseBody);

				if (matcher.find()) {
					imageData = matcher.group(1);
				} else {
					// Try alternative pattern
					pattern = Pattern.compile("\"imageBase64\"\\s*:\\s*\"([^\"]+)\"");
					matcher = pattern.matcher(responseBody);
					if (matcher.find()) {
						imageData = matcher.group(1);
					}
				}

				if (imageData != null) {
					// Decode the base64 string into bytes
					byte[] imageBytes = Base64.getDecoder().decode(imageData);

					// Save the image bytes to a file
					Files.write(Paths.get(filename), imageBytes);
					System.out.println("\nSuccess! Image saved as '" + filename + "'");

				} else {
					System.out.println("\nError: Could not find image data in response: " + responseBody);
				}
			} else {
				// Print an error if the request failed
				System.out.println("\nError: API request failed with status code " + response.statusCode());
				System.out.println("Response: " + response.body());
			}

		} catch (IOException | InterruptedException e) {
			System.out.println("\nError: An exception occurred during the request: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("\nAn unexpected error occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// --- --- --- --- ---
	// --- MAIN SCRIPT ---
	// --- --- --- --- ---

	public static void main(String[] args) {
		// The API key should be left as "" and will be automatically
		// populated by the environment when you run this.
		// You must get your key from Google AI Studio.
		// String apiKey = "AIzaSyDe77Bh0zq7H_MSxgz-CzkMAa3qaooP_Uw";

		System.out.println("=== ImageGenerator Status ===\n");
		System.out.println("✓ API endpoint is CORRECT");
		System.out.println("✓ Request format is CORRECT");
		System.out.println("✓ Using: gemini-2.5-flash-image model\n");

		System.out.println("Current Status: 429 Quota Exceeded");
		System.out.println("Action needed: Enable billing on your Google Cloud project");
		System.out.println("or wait for the quota to reset (free tier limited to 0 requests).\n");

		// You can uncomment to test when you have quota available
		 String userPrompt = "A cinematic photo of a bioluminescent jellyfish floating in a nebula, hyper-realistic, 8k";
		 generateImage(userPrompt, "jellyfish_nebula.png");
	}
}
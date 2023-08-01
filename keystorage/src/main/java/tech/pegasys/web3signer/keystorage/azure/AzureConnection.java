/*
 * Copyright 2023 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package tech.pegasys.web3signer.keystorage.azure;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AzureConnection {
  private final HttpClient httpClient;

  public AzureConnection(final HttpClient httpClient) {
    this.httpClient = httpClient;
  }

  private static final Logger LOG = LogManager.getLogger();

  public Map<String, Object> executeHttpRequest(final HttpRequest httpRequest) {

    final HttpResponse<String> response;
    try {
      response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    } catch (final IOException | InterruptedException | RuntimeException e) {
      LOG.error("Error communicating with Azure vault:" + e.getMessage());
      throw new RuntimeException("Error communicating with Azure vault: " + e.getMessage(), e);
    }

    if (response.statusCode() != 200 && response.statusCode() != 204) {
      throw new RuntimeException(
          String.format(
              "Error communicating with Azure vault: Received invalid Http status code %d.",
              response.statusCode()));
    }

    return AzureKVResponseMapper.from(response.body());
  }
}

/*
 * Copyright 2022 ConsenSys AG.
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
package tech.pegasys.web3signer.signing.config;

import tech.pegasys.signers.aws.AwsSecretsManager;
import tech.pegasys.signers.aws.AwsSecretsManagerProvider;

public class AwsSecretsManagerFactory {

  public static AwsSecretsManager createAwsSecretsManager(
    final AwsSecretsManagerProvider awsSecretsManagerProvider, AwsSecretsManagerParameters awsSecretsManagerParameters) {
    switch (awsSecretsManagerParameters.getAuthenticationMode()) {
      case SPECIFIED:
        return awsSecretsManagerProvider.createAwsSecretsManager(
            awsSecretsManagerParameters.getAccessKeyId(),
            awsSecretsManagerParameters.getSecretAccessKey(),
            awsSecretsManagerParameters.getRegion());
      default:
        return awsSecretsManagerProvider.createAwsSecretsManager();
    }
  }
}

# kotlin-lambda

Test project to try out Kotlin running on AWS Lambda deployed via CDK.

Following this tutorial:

https://medium.com/geekculture/serverless-aws-with-kotlin-gradle-and-cdk-d6bfe820b85

Also integrated DynamoDb using (alpha) AWS SDK for Kotlin.

## Deployment

```
./gradlew shadowJar
cdk synth
cdk deploy --app stack/cdk.out 
# ... 
cdk destroy --app stack/cdk.out
```

> `cdk synth` will report `ENOENT: no such file or directory, open 'cdk.out/manifest.json'`
>  Just ignore this (see the tutorial)

## Debugging

Installed [AWS SAM CLI](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/what-is-sam.html), and then installed the [AWS Toolkit for JetBrains IntelliJ IDEA](https://docs.aws.amazon.com/toolkit-for-jetbrains/latest/userguide/welcome.html).

The AWS IntelliJ plugin functions. I can explore my AWS services. However, it seems to only work with SAM applications (with `template.yaml` file). This being a CDK app, the plugin had no clue what to do with my local code.

Next, experimented with CDK debugging using (beta) [sam-beta-cdk](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-cdk.html).

Because of the multi-project setup, had to create a symlink to where SAM expects to find its working files. From project root:
```
mkdir stack/.aws-sam && ln -s stack/.aws-sam .aws-sam
```

Executing the following did, indeed, run the Lambda function locally:
```
sam-beta-cdk local invoke "sample-stack/sample-handler"
```

So that's something, but not finding a good way to step-through debug a CDK Lambda in IntelliJ. Since `sam-cdk` is in beta, maybe AWS Toolkit CDK integration is just over the horizon?
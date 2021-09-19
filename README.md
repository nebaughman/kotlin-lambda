# kotlin-lambda

Test project to try out Kotlin running on AWS Lambda deployed via CDK.

Following this tutorial:

https://medium.com/geekculture/serverless-aws-with-kotlin-gradle-and-cdk-d6bfe820b85

Also integrated DynamoDb using alpha AWS SDK for Kotlin.

## Development

```
./gradlew shadowJar
cdk synth
cdk deploy --app stack/cdk.out 
# ... 
cdk destroy --app stack/cdk.out
```

> `cdk synth` will report `ENOENT: no such file or directory, open 'cdk.out/manifest.json'`
>  Just ignore this (see the tutorial)

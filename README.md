# kotlin-lambda

Test project to try out Kotlin running on AWS Lambda deployed via CDK.

Following this tutorial:

https://medium.com/geekculture/serverless-aws-with-kotlin-gradle-and-cdk-d6bfe820b85

Also integrated DynamoDb -- using alpha AWS SDK for Kotlin!

## Caveat

Warning: This does not entirely work in current state!

Lambda is created with default execution role, which does not have access the DynamoDb. I manually added access to put items in to the db, and the stack worked.

This hand-tweaking the stack's IAM Role used for the Labmda caused `cdk destroy` to crash. Also could not delete the stack from the AWS Console. Had to manually delete the role, then could delete the stack.

Need to augment code to create a role for the Lambda with specific access to PutItem only to the database created in the stack.

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

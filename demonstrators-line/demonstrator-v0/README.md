## PURPOSE
Discover the design and implementation informations regarding the technology stack MPP initial version.

# TECHNOLOGIES STACK ANALYSIS
One processed analysis of several technologies identified as eligible to TechStack catalog is presented at [technologies-stack-analysis](technologies-stack-analysis.md) to discover all the evaluated opportunities.

# TECHNOLOGY DEMONSTRATORS
Find at [v0-technologies-stack](v0-technologies-stack.md) the current demonstrator version designed and coded allowing to present the result of the technology stack aggregation into a CYBNITY functional usage.

## MANUAL PUBLISH TO MAVEN CENTRAL REPOSITORY
CYBNITY Continuous Integration platform is only supporing an automatic publish of a demonstrator version into CYBNITY maven repositories.

For publish into Maven Central Repository:
- local environment variable (named `MAVEN_GPG_PASSPHRASE`) shall be defined regarding the GPG passphrase allowing signing of the demonstrator for the `gpg.keyname` defined as property of the demonstrator pom.xml

- local maven `settings.xml` shall be defined including authorized User token informations

```
<server>
    <id>central</id>
    <username>XXXXXXXX</username>
    <password>YYYYYYYYYYYYYYYYYYYY</password>
    <!-- env variable MAVEN_GPG_PASSPHRASE shall be defined with private key passphrase or given manually from prompt when a deploy command is executed to publish on Maven Central Repository -->
</server>
```

- a maven command line action shall be executed since a demonstrator folder as:

```
mvn -Denvironment=central-deploy clean deploy
```

When the maven command execution is confirmed in success, the user shall go to Maven Central Repository UI (since View deployments screen) for validation of the PUBLISH request (manual step over connected account) to confirm the real deployment according to the conformity checks performed by Sonatype on eligible artefacts version as official public available version.

#
[Back To Home](../README.md)

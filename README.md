GitHub Repo Client

A Spring Boot-based application that connects to the GitHub API and retrieves public repository and branch data for a given user. The application is designed to be part of a recruitment project, following clean code principles and professional standards.

Features
--------
- Fetches all public repositories for a given GitHub username.
- Retrieves all branches for each repository along with the latest commit SHA.
- Handles invalid usernames and improper GitHub credentials gracefully.
- Supports both environment variable configuration and interactive CLI prompts for private key path and GitHub App ID.

Technologies Used
-----------------
- Java 21
- Spring Boot 3.4.4
- OpenFeign (for GitHub API communication)
- JUnit 5 (unit and integration testing)
- Maven
- BouncyCastle (for decoding PKCS#1 private keys)

Prerequisites
-------------
- Java 21 installed
- Maven installed
- GitHub App credentials:
  - Private key (.pem format, PKCS#1)
  - GitHub App ID
 
    
How to Obtain GitHub App ID and Private Key
===========================================

To use this application, you need a GitHub App with the proper permissions.
Follow these steps to create one and retrieve your credentials:

1. Create a GitHub App
-----------------------
1. Go to: https://github.com/settings/apps
2. Click “New GitHub App”
3. Fill out the form:
   - App name: any name (e.g., GitHub-Repo-Client)
   - Homepage URL: your GitHub profile or project page (e.g., https://github.com/your-username)
   - Webhook URL: leave empty (unless you need it)
   - Repository permissions:
       - Contents: Read-only
       - Metadata: Read-only
   - Uncheck “Expire user authorization tokens” if not needed
4. Click “Create GitHub App”

2. Get the App ID
------------------
After creating the app, you will see your App ID on the App settings page.

3. Generate and Download a Private Key
---------------------------------------
1. Scroll to the "Private Keys" section
2. Click “Generate a private key”
3. A `.pem` file will be automatically downloaded

IMPORTANT:
----------
- Store your `.pem` key file securely.
- You will use the App ID and the private key in this project (via environment variables or interactive input).
- Do not list your app in the marketplace. It may cause problems.

  
Running the Application
-----------------------
You can run the application in two ways:

1. Downloading Jar file present in "demo" folder
   - to run your jar file type: java -jar demo-GitHub-Client-API.jar in jar direcotry.
2. Compile the code by yourself 

When application is running
---------------------------


Set the following environment variables before running the application:

    GITHUB_PRIVATE_KEY_PATH=/absolute/path/to/your/private-key.pem
    GITHUB_APP_ID=your_github_app_id

Then run:

    mvn spring-boot:run


Running Tests
-------------
To execute all tests (unit and integration):

    mvn test

If a test hangs or you want debug info:

    mvn test -X

API Endpoint
------------
GET /getRepos/{username}

Response:

[
  {
    "name": "RepositoryName",
    "owner": {
      "login": "Username"
    },
    "branches": [
      {
        "name": "main",
        "commit": {
          "sha": "latestCommitSha"
        }
      }
    ],
    "fork": false
  }
}

Errors:
- 404 Not Found – If the user does not exist or has no public repositories.

Building a JAR
--------------
To package the project as a JAR:

    mvn clean package
    or 
    mvn clean install

The JAR will be available in the target/ directory:

    java -jar target/GitHub-Repo-Client-0.0.1-SNAPSHOT.jar

Notes
-----
- This project is intended as a recruitment assignment.
- Only public repositories and branches are fetched – no authentication for private resources is performed.

License
-------
This project is provided for educational/recruitment purposes and is not licensed for commercial use.

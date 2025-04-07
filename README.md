GitHub Repo Client
==================

A Spring Boot-based application that connects to the GitHub API and retrieves public repository and branch data for a given user.

The project is split into two branches:

- `no-auth` – A simple version without authentication. It uses unauthenticated access to GitHub's public API.
- `master` – A version that uses GitHub App authentication (App ID + private key) for secure access and higher rate limits.

Features
--------
- Fetches all public repositories for a given GitHub username.
- Retrieves all branches for each repository along with the latest commit SHA.
- Handles invalid usernames and improper GitHub credentials gracefully.
- Supports environment variable configuration (only in `master` branch).
- Clean architecture and testable service design.

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

If using the `master` branch:
- GitHub App credentials:
  - Private key (.pem format, PKCS#1)
  - GitHub App ID

How to Obtain GitHub App ID and Private Key (for `master` branch)
==================================================================

To use the authenticated version, you need a GitHub App with the proper permissions.  
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

First, clone the project and switch to the desired branch:

    git clone https://github.com/PatriStelmach/GitHub-Repo-Client.git
    cd GitHub-Repo-Client

    git checkout no-auth   # or: git checkout master

Then run the application:

    mvn spring-boot:run

If using the `master` branch, set the following environment variables before running the application:

    GITHUB_PRIVATE_KEY_PATH=/absolute/path/to/your/private-key.pem
    GITHUB_APP_ID=your_github_app_id


Running Tests
-------------
To execute all tests (unit and integration):

    mvn test

If a test hangs or you want debug info:

    mvn test -X
The app is running on port 8090 which you can change in application properties
------------------------------------------------------------------------------
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
]

Errors:
- 404 Not Found – If the user does not exist or has no public repositories.

Building a JAR
--------------
To package the project as a JAR:

    mvn clean package
    or 
    mvn clean install

The JAR will be available in the `target/` directory:

    java -jar target/GitHub-Repo-Client-0.0.1-SNAPSHOT.jar

Notes
-----
- Only public repositories and branches are fetched.
- The `master` branch uses GitHub App authentication for greater limit from API access; the `no-auth` branch is limited to 60 request per hour only.

License
-------
This project is not licensed for commercial use.

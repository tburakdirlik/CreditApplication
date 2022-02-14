# CreditApplication
### Requirements
Docker desktop
run below command in the terminal 
```
docker pull postgres
```
### Dependencies:
Lombok, Spring Data Jpa, Spring Web, PostgreSQL Driver
### Libraries:
For sms sending, Twilio library used. 

Twilio uses the application.yml file and pulls the information from it.
I added this information to the code because it gave an error when using trial_number.
Trial_number has been added in line 28 in TwilioSmsSender class.
Only registered numbers in Twilio account are saved in PostgreSQL database, so that 
if you disable this service just delete lines 52,53 -- 188,189,190 lines in the Controller class which under the CreditApi package.
This service used at addUser and updateUser methods.

These repo were referenced and adapted to the project --> https://github.com/amigoscode/springboot-twilio

PostgreSQL is running as a docker container.
For this, go to the docker-compose.yml file and run the following command from the terminal.

```
docker-compose -f .\docker-compose.yml up -d
```

<p align="center">
  <img src="https://raw.githubusercontent.com/tburakdirlik/CreditApplication/main/SS/1.png?token=GHSAT0AAAAAABP7K6TKGAHRZGMQN7VKFX5YYQTFGTA" />
</p>

<p align="center">
  <img src="" />
</p>

<p align="center">
  <img src="" />
</p>



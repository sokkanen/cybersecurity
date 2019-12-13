## CYBER SECURITY BASE - COURSE PROJECT REPORT

https://github.com/sokkanen/cybersecurity

Program running in Heroku...

---
#### FLAW 1: SQL INJECTION:

The developer has decided to use her own implementation of db-access instead of JPARepository.
She has also made it possible to check if the registration to the event was successful by allowing
users to search the registration database with their name. Unfortunately the query parameters are 
appended to the sql-scripts without any kind of validation. This makes the software vulnerable for SQL-injections.

For example: When entering `' OR 1=1;--` to the search-form, the user gets to see all the registrations in the database.
If the hacked would like to insert fake signups, he could do so by entering 
`' OR 1=1;INSERT INTO Signup(name, address) VALUES ('fake', 'hacked'));--` to the search.

##### HOW TO FIX:

Variable parameters shouldn't be appended to the SQL-queries, but inserted with safe methods. 
Three options are as follows:
1. When there is no need for high customization, you could use `JPARepository`'s pre-defined methods, 
which are safe from SQL-injections.
2. When customizing JPARepository's behavior with custom queries, it is necessary to set the parameters 
with `Query`'s `setParameter` - method.
3. If the use of custom implementation is necessary, the parameters should be added via 
`PreparedStatement`'s `setInt(), setString` or similar methods.

---

#### FLAW 2: Cross-Site Scripting (XSS)

When registering to the event, the attendee has an opportunity to send messages to other future attendees. 
Even if the SQL-side of the messages is safe from injection-attacks, the Thymeleaf template has a nasty 
flaw in defining the attribute types. Developer has used Thymeleaf's unescaped text `utext` instead of 
regular `text`, which allows Javascript to be run inside the element. This makes the template vulnerable to
XSS-attacks. For example: When submitting a message: `<SCRIPT>window.alert("PWND!"")</SCRIPT>`, the next user
loading the page will have a "PWND!" window pop-up displayed. The Script could be used to something much 
more serious, such as sending user and session information to the attacker. 

##### HOW TO FIX:

The "done" template should be updated with `text` attribute-definition. This prevents the Javascript's
`<SCRIPT>` to be run inside the template.

---

#### FLAW 3: BROKEN AUTHENTICATION

As stated in the OWASP Top 10 of 2017, one criteria for broken authentication is 
"Permits default, weak, or well-known passwords, such as "Password1" or "admin/admin“". This program
unfortunately has very naive password protection in the /admin -view. Default username and password
are hard-coded to the application. Not only this, but the username and password are quite easy to guess as
they are `admin/password`.

##### HOW TO FIX:

Default, hard-coded usernames / passwords should never exist at all. All the username/password -combos should
be individually defined. All passwords should be deleted from the application after initial creation and 
later hash creations. Only password hashes should ever be stored into the database. All logins should compare
stored password-hash with the hash of the user entered password, never actual plain text passwords. Spring security's
authentication should be enabled in the application.

---

#### FLAW 4: SENSITIVE DATA EXPOSURE

The application has multiple endpoints where sensitive data exposed. This vulnerability is closely related
to the SQL-injection vulnerability, as injection allows hacker to fetch signup-information from the application's
database. Furthermore /admin -view provides the admin a possibility to change the admin password, which was
`password` by default... Unfortunately there is only one common password for admins, and it is stored into the 
database without any hashing. Therefore admin password can be fetched in plain text with a successful SQL-injection. 

##### HOW TO FIX:

There is no reason to return a list of results, when the purpose of a feature is to get information on single user.
Sloppy and naive copy-paste code can work, but is can also make the application more vulnerable. The logic used in 
the source code should be as strict as possible.

Passwords should never be stored in plain text. All individual users should have individual passwords; There should
be no master passwords in production.

---

#### FLAW 5: INSUFFICIENT LOGGING AND MONITORING

As stated in the OWASP Top-10: "Exploitation of insufficient logging and monitoring is the bedrock of nearly every 
major incident.". Lack of adequate logging means that it could potentially take a long time before any kind of 
data breach or attack would be noticed.

Logging in the application is done very naively, as logged rows only contain general information without any parameters, such as
"SQL-operation OK" or "SQL-operation failed". Logging for errors or critical operations (such as administrator 
password change..) is never done properly. All logging is done on the "INFO"-level, so building an alert based on 
current logging would be impossible.

##### HOW TO FIX:

Logging should be divided to different levels, such as "INFO", "SEVERE" and "WARN". At least, the 
critical operations should contain parameterized information about the incident. 
For example: `catch(Exception e) --> logger.warning("SQL operation for change admin password failed with message {}, 
e.getMessage())`. Automatic monitoring with automated e-mail alerts on severe- and warning-level of logs should
be added to the application's running environment.

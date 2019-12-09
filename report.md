CYBER SECURITY BASE - COURSE PROJECT REPORT

##LINK: <link to the repository>
<installation instructions if needed>
---
####FLAW 1: SQL INJECTION:

The developer has decided to use her own implementation of db-access instead of JPARepository.
She has also made it possible to check if the registration to the event was successful by allowing
users to search the registration database with their name. Unfortunately the query parameters are 
appended to the sql-scripts without any kind of validation. This makes the software vulnerable for SQL-injections.
For example: When entering `' OR 1=1;--` to the search-form, the user gets to see all the registrations in the database. 

#####HOW TO FIX:

Variable parameters shouldn't be appended to the SQL-queries, but inserted with safe methods. 
Three options are as follows:
1. When there is no need for high customization, you could use `JPARepository`'s pre-defined methods, 
which are safe from SQL-injections.
2. When customizing JPARepository's behavior with custom queries, it is necessary to set the parameters 
with `Query`'s `setParameter` - method.
3. If the use of custom implementation is necessary, the parameters should be added via 
`PreparedStatement`'s `setInt(), setString` or similar methods.


####FLAW 2: Cross-Site Scripting (XSS)

When registering to the event, the attendee has an opportunity to send messages to other future attendees. 
Even if the SQL-side of the messages is safe from injection-attacks, the Thymeleaf template has a nasty 
flaw in defining the attribute types. Developer has used Thymeleaf's unescaped text `utext` instead of 
regular `text`, which allows Javascript to be run inside the element. This makes the template vulnerable to
XSS-attacks.

#####HOW TO FIX:

The "done" template should be updated with `text` attribute-definition. This prevents the Javascript's
`<SCRIPT>` to be run inside the template.


####FLAW 3: SENSITIVE DATA EXPOSURE
<description of flaw 2>
<how to fix it>

...

####FLAW 5: VULNERABLE INFORMATION... PASSWORDS ARE SAVED....
<description of flaw 5>
<how to fix it>
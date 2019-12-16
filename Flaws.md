

### Flaw 1: Security misconfiguration 
#### Description: Security misconfiguration by not using CSRF-tokens


CSRF tokens can be used to prevent Cross-site request forgery (CSRF) attacks and also some Cross-site scripting (XSS) attacks. CSRF allows an attacker to induce a victim user to perform actions that they do not intend to. CSRF can be described as a "one-way" vulnerability, the attacker can induce the victim to issue an HTTP request, they cannot retrieve the response from that request. XSS then again is a "two-way" in that attacker's injected script can issue arbitrary requests and read the responses. 

If the server validates the CSRF token, and rejects the requests without a valid token, then the token does prevent the exploitation of also and XSS vulnerability. This however assumes that CSRF tokens protect all the relevant requests. 

Java Spring Security has CSRF tokens set on by default, so if one wants them not be used, they must be disabled. In the project this misconfiguration is caused by the line 24 in the SecurityConfiguration.java -class. It disables the use of these tokens. In the OWASP Top 10 Application Security Risks, this would flaw would fall into the category A6:2017 - Security Misconfiguration.

The SecurityConfiguration.java -class is also otherwise flawed. At the moment it does not include any specifications on to which pages the user (or in fact anyone) is allowed to enter. So even if the person has not signed in, the application would allow them to enter all the pages of the application and therefore the application is vulnerable to attacks. 

#### Fix: Remove the faulty line and add more configuration

The first and foremost fix would be to remove that line 24 from the said class. This would enable CSRF-tokens again. Also some more configuration would be needed in that specific class, to block the usage from non-authenticated visitors to prevent other kinds of attacks. For instance, the specification: 

    http.headers().frameOptions().sameOrigin();

    http.authorizeRequests()
        .anyRequest().authenticated().and().formLogin().loginPage("/form").permitAll();

would allow all users to access the pages "/" and "/form" but all other paths require authentication. When a user successfully submits the form, they will be redirected to the previously requested page that required authentication. Also a user-role specification would be needed. 

### Flaw 2: Injection 
#### Description: Vulnerability for SQL Injections

Whenever a database is used, it is essential to sanitize and validate the data the user can put into the system. At the moment the data entered in the form is not validated in any shape or form (Lines 10-14, in the form.html -template). Therefore anyone can insert malicious SQL-queries there, as the data enters the database straightaway. For instance I was able to insert the query "SELECT * FROM signup WHERE name = 'smith' OR 'x'='x';" without any problems.

#### Fix: Validation and sanitazion of the user inputs. 

For instance the data should be checked and only letters a-z, or A-Z and numbers should be allowed. Now also special characters like ";" are allowed, which enables malicious SQL-queries enter the system. Also the inputs should be validated considering length, that they are not too short or too long and the person has not already signed up. These checks should be done, before the query-statement is done



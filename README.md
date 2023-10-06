### Home Rental Booking System
## cross-UI between reservation management for owner and reservation booking UI for guests

## Program set-up instructions

### 1. Clone the code from Github
### 2. Open Eclipse (assuming they have Eclipse downloaded)
### 3. Import the code from 'File System' 
### 4. Import LGoodDatePicker-11.2.1.jar AND sqlite-jdbc-3.36.0.3.jar file AND javax.mail.jar AND javax.activation.jar

- Right click on the project
- Choose 'Build Path' --> 'Configure Build Path' --> in the Libraries tab choose 'Classpath'
- Click 'Add External JARs' --> choose the file path of the LGoodDatePicker file
- once clicked 'Open', click 'Apply and Close'
- on the toolbar, click on the drop down menu and choose 'Run Config.' 
- on the 'Dependencies' tab, click on 'Classpath Entries' --> 'Add External JARs'
- choose filepath of the LGoodDatePicker and the sqlite file, click 'Open'
- click 'Apply and Close' 

### 5. Set up environment variable(s)
Go to 'Run Config.' --> 'Environment' tab --> click on 'Add' button.
Type your variable name and the value. 
	
Environment variable list: 
	
* DB_URL: Format `jdbc:sqlite:<path>`; the URI/pathway to connect to your local database
* EMAIL_PASSWORD: the password of the sender's email
	

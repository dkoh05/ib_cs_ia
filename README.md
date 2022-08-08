# How to run and open the code (assuming you're using Eclipse)

### 1. Clone the code from Github
### 2. Open Eclipse (assuming they have Eclipse downloaded)
### 3. Import the code from 'File System' 
### 4. Import LGoodDatePicker-11.2.1.jar and sqlite-jdbc-3.36.0.3.jar file

- Right click on the project
- Choose 'Build Path' --> 'Configure Build Path' --> in the Libraries tab choose 'Classpath'
- Click 'Add External JARs' --> choose the file path of the LGoodDatePicker file
- once clicked 'Open', click 'Apply and Close'
- on the toolbar, click on the drop down menu and choose 'Run Config.' 
- on the 'Dependencies' tab, click on 'Classpath Entries' --> 'Add External JARs'
- choose filepath of the LGoodDatePicker and the sqlite file, click 'Open'
- click 'Apply and Close' 

### 5. Set up environment variable(s)
- Go to 'Run Config.' --> 'Environment' tab --> click on 'Add' button
- Type your variable name and the value (which is the path to your database file)
	Note: Set the name as 'DB_URL' and the value depending on your path to database file. 
	The format of 'value': `jdbc:sqlite:<path>`
	

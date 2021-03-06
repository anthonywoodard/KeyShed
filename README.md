KeyShed v1.1.0
-------

KeyShed is a Java-based command line tool that stores access credentials for the Mac.

KeyShed uses the following open source projects:

* Apache Maven - a software project management and comprehension tool
* Spring Framework - a comprehensive programming and configuration model for modern Java-based enterprise applications
* SQLite - a software library that implements a SQL database engine

Setup
---------------------
These instructions assume you have Apache Maven installed.

1. Check out the project from:

    https://github.com/anthonywoodard/KeyShed.git

2. In Terminal, navigate to the KeyShed project directory

    cd /path/to/KeyShed
		
3. Execute the exeScript.sh script (Update will not overwrite database)

    sh exeScript.sh    

Start
-------

Start KeyShed by typing <b>keyshed</b> at the prompt or type <b>keyshed</b> with one of the following options:

    * backup [directory] - backup the KeyShed database to a directory
    * help
    * recover - recover the KeyShed database from an internal copy
    * restore [file]  - restore the KeyShed database using another KeyShed database file
    * timestamp - display the last time the KeyShed database was moved
    * uninstall

Use
------

Command Format: command -[parameter]

User Commands:

    * user -del
    * user -list
    * user -login
    * user -new
    * user -password
    * user -register

CRUD Parameters: 
 
* c - category 
* i - id 
* p - password
* t - title
* u - username
* w - url

CRUD Commands:

    * copy -i=[] or -t=[] (title must be unique. will copy password to clipboard.)
    * del -i=[] or -t=[] (title must be unique)
    * find -i=[] or -c=[] or -t=[] (Default Sort: Ascending by category, title)
    * list (table view) (Default Sort: Ascending by category, title) (Optional Sort Parameters: -o=i || -o=c,p,t,u,w)
    * llist (list view) (Default Sort: Ascending by category, title)
    * new (Required: -t=[] and -u=[] and -p=[]) (Optional: -c=[] or -w=[])
    * update (Required: -i=[]) (Optional: -c=[] or -p=[] or -u=[] or -w=[] or -t=[])

Export Parameter: 

* f - file to export to

Export Command:

     export (Required: -f=[])

Import Parameters: 

* f - csv file to import
* r - first row contains column headers

Import Command:

     import (Required: -f=[] and -r=[true or false])

General Commands:

    * help
    * quit
    * exit
    
Architecture
------  

View <--> Controller <--> Service <--> Model

KeyShed employs the Model View Controller (MVC) pattern to make it easier to implement
different views in the future.  Additionally, the Service and Data Access Object (DAO) patterns 
are used to manage the business logic and data access operations respectively.

      
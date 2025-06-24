# Instructions for using config.properties

## About config.properties

This file is used to store the url for a locally hosted MySQL database and the users credentials for accessing the database.

This file has 3 fields...

1. `db.url`: this is the url for the database
2. `db.username`: this is ***your*** username for the db connection
3. `db.password`: this is ***your*** password for the db connection

## Using and Modifying this file

### db.url

This field needs to be changed to the url of your locall db

This is what the url field looks like before configuration

```config
db.url=
```

This is how it should be formatted after config

- ***NOTE***: All of these sample configurations are probably not going to be exactly right for your specific setup. Please read the instructions, copy and paste at your own risk

```config
db.url=jdbc:mysql://localhost:3306/progress_tracker?serverTimezone=EST5EDT
```

- `jdbc:mysql`: this field tells jdbc what kind of db you are working with.  
    Since I am developing using MySQL, so that is what is plugged in here. If you are not using MySQL, you will need to change this to your db.
- `localhost:3306`: this field tells jdbc where the db is being hosted.  
    If your db is not locally hosted, you will need to change the localhost to the actual url of your db.  
    The default port for MySQL db is 3306, if you changed the port number for your db, change 3306 to the port your db is listening on.
- `progress_tracker`: this is the name of your db schema.  
    If you modified the name of the db in `init_db.sql`, change "progress_tracker" to the name of the db in the script
- `serverTimezone=EST5EDT`: This is the timezone the db is set to.
    This field may or may not need to be present depending on what platform you are running this on. However, I would recommend including it regardless.  
    You can change the timezone set on the right hand side of the =, if you want to specify which timezone the db is configured to.  
    If you change the timezone, it must be one that the db server and jdbc recognize

### db.username

This field needs to be changed to the username you registered with your locally hosted db.

This is what the username field looks like before configuration

```properties
db.username=
```

An example of what this field could look like when configured

```properties
db.username=MyDBUsername
```

### db.password

This field needs to be changed to the password you registered with your locally hosted db.  

This is what the password field looks like before configuration

```properties
db.password=
```

An example of what this field could look like when configured

```properties
db.username=MyDBPassword
```

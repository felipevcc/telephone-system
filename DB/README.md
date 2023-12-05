# Database configuration

To initialize and configure the database, follow these steps:

First of all, the datafile path must be set in the [TS_APP_ASIG_NUM_TEL.sql](https://github.com/felipevcc/telephone-system/blob/main/DB/sys/tablespaces/TS_APP_ASIG_NUM_TEL.sql) file.

To configure the encoding in `UTF8`, the `NLS_LANG` environment variable must be set.
The first thing is to see if it is already configured from `cmd`:
```bash
set NLS_LANG
```

If it is not configured, it must be configured with the following command:
```bash
set NLS_LANG=.AL32UTF8
```	
This command sets the environment variable temporarily for the current session. To permanently configure it must be created from the user interface in system environment variables.

Once you have this temporary environment variable, open `sqlplus` from the path `telephone-system/DB/`
```bash
sqlplus
```

And finally to load the database you must run the configuration script:
```
@SCHEMA_SCRIPT.sql
```

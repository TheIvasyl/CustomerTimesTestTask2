# CustomerTimesTestTask2

When you run an application it starts with SplashScreen, where the data is pulled from network and inserted to a database. It might take some time, since there is relatively big volume of data. Then it starts main activity, which initializes a RecyclerView and loads first page (40 items) of records form database and presents it on a screen as a list. On scroll it loads an another page.

The records Json life (Querry.json) was trimmed to 723 elements, since most of free mocking services do not support big Json responses.

Also, I didn't add progress bars on loading data, so be patient and wait a bit while data is getting loaded. Especially while database is getting filled with records. 

I am aware that querries to a database are not optimal and can be refined. 

RxJava was not used in an app since there was no severe need for it. 

Android Architecture Components were not used because we didn't have any specific models for a database and network services.

Since Account objects contain some fields that were not specified in a Describe.json file, I added a hardcoded crutch to avoid "noSuchColumn" error.

Also on UI I hardcoded to present and "Id" value in a list, so items look more tidy (because every row in a database contains 190 fields).

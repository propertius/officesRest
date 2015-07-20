OFFICES
=======
Dependency management and tasks execution are performed with Gradle.
To run tests manually:
```shell
$ gradle test
```
## Distribution
To create standalone distribution:
```shell
$ gradle distZip
```
Resulting ZIP file contains bin directory with OS specific startup scripts.
API can be seen through the [WADL](http://localhost:8080/api/application.wadl).
To create WAR:
```shell
$ gradle war
```
Once the war is created, it can be deployed and a simple frontend developed with AngularJS can be accessed to test the API [here](http://localhost:8080/page).

## API Design
####Create office
In order to create an office, one should issue the following PUT request
```shell
PUT /api/offices
```
with a JSON object as parameter. Please note the hour format (hh:mm:ss).
```shell
{
    "location": "Berlin",
    "timeDifference": "2",
    "openFrom": "09:00:00",
    "openTo": "18:00:00"
}
```
####List offices
The next request will list the offices
```shell
GET /api/offices
```
If our list were to contain three offices, they would be returned in JSON format, as follows
```shell
[
  {
    "id": 1,
    "location": "Berlin",
    "timeDifference": 2,
    "openFrom": "09:00:00",
    "openTo": "18:00:00",
    "link": "/api/offices/1"
  },
  {
    "id": 2,
    "location": "Buenos Aires",
    "timeDifference": -3,
    "openFrom": "09:00:00",
    "openTo": "18:00:00",
    "link": "/api/offices/2"
  },
  {
    "id": 3,
    "location": "Beijing",
    "timeDifference": 8,
    "openFrom": "09:00:00",
    "openTo": "18:00:00",
    "link": "/api/offices/3"
  }
]
```
Both `offset` and `count` are accepted as query parameters. Assuming the same set of data, if we issue the following request
```shell
GET /api/offices?offset=1&count=1
```
we get the following response from the server
```shell
[
  {
    "id": 2,
    "location": "Buenos Aires",
    "timeDifference": -3,
    "openFrom": "09:00:00",
    "openTo": "18:00:00",
    "link": "/api/offices/2"
  }
]
```
####List offices open now
In order to obtain the offices open at a specific moment, the following `GET` request should be issued, with both local `time` and `utc` offset as parameteres. Note the `time` format.
```shell
GET /api/offices/open?utc=-3&time=10:00%20AM
```
Assuming again the same dataset, the JSON response would be
```shell
[
  {
    "id": 1,
    "location": "Berlin",
    "timeDifference": 2,
    "openFrom": "09:00:00",
    "openTo": "18:00:00",
    "link": "/api/offices/1"
  },
  {
    "id": 2,
    "location": "Buenos Aires",
    "timeDifference": -3,
    "openFrom": "09:00:00",
    "openTo": "18:00:00",
    "link": "/api/offices/2"
  }
]
```
####See details of an office
The following `GET` request retrieves a specific office by `id`.
```shell
GET /api/offices/{id}
```
####Delete an office
Issuing a `DELETE` request with the following sintax will delete an office resource by `id`.
```shell
DELETE /api/offices/{id}
```

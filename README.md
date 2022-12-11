# Exchange Rate service

Simple Springboot application to convert currency using European Central Bank exchange rates

## How to run

This project uses Gradle as the build automation tool.

### Prerequisites

* Java 17

#### Build the application

```.\gradlew build -x test```

#### Run the Application

```.\gradlew bootRun```

#### Access the API
Swagger UI available at http://localhost:8080/swagger-ui/index.html

## Todo
1. AOP - logging
2. Record no of times symbolsDto were called
   1. Save to database
   2. Should not block
      1. Use a blocking queue 
      2. Scheduling / or pub sub


## License Information

<pre>
        Copyright(c) 2022 M.S. Wickramarathne

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

            https://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
</pre>
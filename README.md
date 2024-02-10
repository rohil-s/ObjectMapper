# Jackson And the Object Mapper

## What is Object Mapper? What is it used for?

As per the official docs,

Jackson has been known as "the Java JSON library" or "the best JSON parser for Java". Or simply as "JSON for Java".

Jackson is a suite of data-processing tools for Java (and the JVM platform), including the flagship streaming JSON parser / generator library, matching data-binding library (POJOs to and from JSON) and additional data format modules to process data encoded in Avro, BSON, CBOR, CSV, Smile, (Java) Properties, Protobuf, TOML, XML or YAML; and even the large set of data format modules to support data types of widely used data types such as Guava, Joda, PCollections and many, many more (see below).

While the actual core components live under their own projects -- including the three core packages (streaming, databind, annotations); data format libraries; data type libraries; JAX-RS provider; and a miscellaneous set of other extension modules -- this project act as the central hub for linking all the pieces together.

A good companion to this README is the Jackson Project FAQ.

> As per my understanding Jackson is supposed to be a multipurpose data handling utility that can allow for seamlessly converting data between datatypes in Java. Its predominently used for serializing and deserializing between JSON to POJOs.

## How does it work under the hood?

TBD

## What can be done with Object Mapper?

It can do the following:

1. Serialize POJOs to JSON string representation.
2. Deserialize JSON strings to POJOs.
3. Covert between generic data types and analogous data model classes.

## How to install object mapper with Gradle/Maven?

Add the following dependencies to the `build.gradle` file.

```
implementation group: 'com.fasterxml.jackson.core', name: 'jackson-core', version: '2.16.1'
implementation group: 'com.fasterxml.jackson.core', name: 'jackson-databind', version: '2.16.1'
implementation group: 'com.fasterxml.jackson.core', name: 'jackson-annotations', version: '2.16.1'
```

3 jackson packages will be installed:

1. Jackson Core: contains core low-level incremental ("streaming") parser and generator abstractions used by Jackson Data Processor.
2. Jackson Databind: contains the general-purpose data-binding functionality and tree-model for Jackson Data Processor.
3. Jackson Annotations: contains general purpose annotations for Jackson Data Processor, used on value and handler types.

## Notes:

> **Most of the points discussed below are covered in this code package.**

-   Jackson Databind is the package that contains the ObjectMapper class.
-   When performing simple binding conversions with Maps/Lists/sets, etc use TypeReference from form jackson core to avoid unchecked conversion warning. \
    E.g. `Map<String, Integer> map = mapper.readValue("{\"number\":123}", new TypeReference<Map<String, Integer>>() {});`
-   Serialization is done using: ObjectMapper's `writeValueAsString(Object object)` method.
-   Deserialization is done using: ObjectMapper's `readValue(String jsonString, TypeReference<T> type)` method. This is an overloaded method. It can also accept native Java classes representing schema to hold data.
-   Dealing with Enums can be tricky. There are some inconsistency in what json deserializes to an Enum and what an Enum serializes to. To ensure consistency, do the following:

    1. Annotate the enum with `@JsonFormat(shape = JsonFormat.Shape.OBJECT)`. This will ensure that the enum is serialized to a sub object depicting the enum. If the enum is defined as follows `Enum(EX(val=X))`, this is enclosed in a wrapper class `ABC(Enum fieldName = Enum.EX)`, then the serialized output will look as `{"fieldName": {"val":"X"}}`.

    2. Annottate the value in your Enum using `@JsonValue` annotation.

        - During deserialization, the value of the input is compared with this value to pick the correct Enum value.
        - During serialized as key value where key is the name of the attribute in the parent class and value is the Enum value assigned to it.

        With the same above example, the `ABC`'s object will be serialized to `{"fieldName":"X"}`. Same input will also be deserialized to the Enum value in the parent class as `ABC(Enum fieldName = Enum.EX)`.

-   Incase you have multiple fields in your Enum pick any one to act as a key for the Enum. If that is not possible then ..... ?

-   Optional fields can be skipped during serialization and deserialization when their value is null using the annotation at the data model class: `@JsonInclude(JsonInclude.Include.NON_NULL)`.
-   We can also compeletly ignore any meta fields, which are not required to be present in the serialized output by annotating individual properties of the data model class with `@JsonIgnore` annotation. These fields are also ignored during deserialization.
-   We can also ignore all objects of a certain type. Annotate the class with `@JsonIgnoreType` whose instances need to be skipped during Serialization. Fields of this type are also ignored during deserialization.
-   Use `@JsonProperty("custom_property_name")` to give a custom key for the annotated property in the serialized output. Similarly the deserialization is done from the same `custom_property_name`.
-   Use `@JsonIgnoreProperties(ignoreUnknown = true)` on the data model classes which need to be deserialized such that any
    unknown properties in the input json string are ignored. Alternately you can set this behaviour at the mapper level by initializing it as \
    `ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);`
-   Following affect when you can or cannot serialize/deserialize a field,
    -   A Public Field is both serializable and deserializable.
    -   A Getter Makes a Non-Public Field Serializable and Deserializable.
    -   A Setter Makes a Non-Public Field Deserializable only.

# Postgresql Code generation of UDT record arrays fail to compile in Java

This is a minimal example to reproduce the issue of Postgresql code generation of UDT record arrays failing to compile
in Java.

This is caused by the "interfaces" option on the generator, that assumes (wrongly), that the array type is an interface,
when it is actually an array.

Using the following schema:

```postgresql
CREATE TYPE tag AS
(
    name  text,
    value text
);
CREATE TABLE test
(
    id   SERIAL PRIMARY KEY,
    tags tag[]
);
```

And generating with the following config section:
```java
public void generate() {
    // ...
    new Generator().Generate()
            .withInterfaces(true);
    // ...
}
```

Will generate broken code for the `tags` field in the `TestRecord` class:

```java

@Override
public void setTags(ITag[] value) {
    if (value == null)
        set(1, null);
    else
        set(1, value.into(new TagRecord[] ()));
}

```


## Steps to reproduce

Broken case:
```bash
./gradlew run
```

Outputs:
```
...
> Task :gen:compileJava FAILED
/Users/nthalk/local/src/JooqCustomArrayTypeMve/gen/src/main/java/example/tables/records/TestRecord.java:55: error: array dimension missing
            set(1, value.into(new TagRecord[]()));
...
```

Working case:
```bash
./gradlew run --args='--no-interface-workaround'
```
Outputs: 
```
...
21:47:57.878 [main] INFO JooqCustomArrayTypeMve -- Compiling generated project...
Starting a Gradle Daemon, 1 busy and 1 incompatible and 5 stopped Daemons could not be reused, use --status for details
> Task :gen:checkKotlinGradlePluginConfigurationErrors SKIPPED
> Task :gen:compileKotlin NO-SOURCE
> Task :gen:compileJava UP-TO-DATE

BUILD SUCCESSFUL in 4s
1 actionable task: 1 up-to-date

BUILD SUCCESSFUL in 8s
2 actionable tasks: 1 executed, 1 up-to-date
```



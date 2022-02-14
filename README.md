# Hachi

Java Discord bot

Doesn't do anything interesting yet. 😔

`hachi-core/` contains the core features of the bot.

`src/` contains the main application, usable commands and database classes.

## Usage

Clone the repository

```shell
git clone https://github.com/ootsuha/Hachi.git
cd Hachi
```

```shell
export HACHI_TOKEN="put bot token here"
./gradlew run
```

## Creating a new HachiCommand

You can create a new command by extending `HachiCommandImpl` with the code to
run placed in the `run` method.

```java
package io.github.ootsuha.hachi.commands;

import io.github.ootsuha.hachi.core.command.*;
import io.github.ootsuha.hachi.core.command.request.*;
import org.springframework.stereotype.*;

@Component
public final class Foo extends HachiCommandImpl {
    public test() {
        super("foo", "Sample command description.");
        // Add command options here
    }

    @Override public void run(final HachiCommandRequest r) {
        // Do stuff here
    }
}
```

Alternatively, you can extend `HachiStringCommand` or `HachiEmbedCommand` if the
command results in a `String` or `MessageEmbed` response from the bot respectively.
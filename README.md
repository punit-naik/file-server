# file-server

[![Clojars Project](https://img.shields.io/clojars/v/file-server.svg)](https://clojars.org/file-server)

Serves a file or directory as static assets on the web server

## Usage

**NOTE**: [Java](https://openjdk.java.net/) and [Leiningen](https://github.com/technomancy/leiningen) must be pre-installed!

### Run

You can run this project in multiple ways, see below:

1. `lein run`

    This will run the server on a random free port and will serve files and folders from the directory this command is being run in.
2. `lein run 3000`

    Same as above, but will run the server on port `3000`
3. `lein run 3000 doc`

    Same as above, but the server will serve files inside the `doc` directory which should be present in the directory you are running this command from.

**NOTE**: After the server has started, it will print out a statement saying on which port the server is running and which directory it is serving.

### Test

```
lein test
```

### Build

```
lein with-profiles +uberjar uberjar
```

## Docs

[`file-server` API Docs](https://punit-naik.github.io/file-server)

### Code Coverage

[`file-server` Code Coverage](https://punit-naik.github.io/file-server/coverage/)

## License

Copyright © 2020 [Punit Naik](https://github.com/punit-naik)

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.

# nopl

A template for node command line scripts. Includes

- nrepl support via piggieback
- basic line-wise reading from stdin
- command line arg parsing

## Usage

To create a new project:

```bash
lein new nopl hello-world
cd hello-world
```

to fire up the repl, do:

```clojure
(require 'cljs.repl.node)
(cemerick.piggieback/cljs-repl (cljs.repl.node/repl-env))
```

## License

Copyright © 2015 Rasmus Buchmann

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

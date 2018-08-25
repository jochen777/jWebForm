# Compare jWebform to jFormchecker

Why did I rewrote the jFormchecker lib?

In short: jFormchecker was not clean code as we know it. 
The library itself did too much things at once, the classes were too big,
the API too bloated.

I tried to design jWebform more carefully and I hope the result is far better:

* The core library does not have any dependency at all.
* Functional style as much as possible: Types are functions, immutable objects ...
* Reduce public methods as much as possible (Example: reduce public methods from 42 (jFormchecker) to 3 (jWebform)
* Allow groups of types
* Simpler creation of new types (Builder pattern)
* More than 100% faster than jFormchecker
* Render HTML in separate project (allow java rendering or template-macros)
* And last but not least: A better name. jFormchecker seemed just to "check" forms, but not to "draw" and define them. jWebform is more generic. And I can use this to implement pyWebform, pWebform, plWebform .... you name it.

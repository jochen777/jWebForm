# Individual Input-Types

If the build in set of Input-Types is not enough for you, you can
easily create your own ones.

First, you have to decide: Single Input-Type or complex?

## Single Input Types

Single input types typically consist of just one input tag. 

To create one, implement the SingleType interface (jwebform.field.structure.SingleType). A good starting point 
is to look at the sourcecode for jwebform.field.TextType.

For very simple types, take a look at jwebform.field.SimpleType

Don't forget to add this type to your template code (or Java-Rendering logic).

## Complex Input Types

If you want an input type, that is constructed out of other input types, 
you have to choose the jwebform.field.structure.GroupType.

A good example is jwebform.field.TextDateType

Don't forget to add this type to your template code (or Java-Rendering logic).  

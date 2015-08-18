
QB4OLAP is a Vocabulary for Business Intelligence over Linked Data.

It is an extension of the [DataCube](http://www.w3.org/TR/vocab-data-cube/) vocabulary that allows to represent OLAP cubes in RDF, 
and to implement OLAP operators (such as Roll-up, Slice, and Dice) as SPARQL queries directly on this RDF representation.

#The vocabulary 

You can find the current version of the vocabulary in Turtle format in this repository. 
The latest version (v1.3) is available [here](https://github.com/lorenae/qb4olap/blob/master/rdf/qb4olap.ttl). Previous stable version of the vocabulary (v1.2) is available [here](https://github.com/lorenae/qb4olap/tree/master/rdf).

The main improvement in v1.3 is the ability to represent custom rollup relationships.

We have defined the following purls to refere to each version:

* Version 1.2: http://purl.org/qb4olap/cubes_v1.2
* Version 1.3: http://purl.org/qb4olap/cubes

We suggest to use dcterms:conformsTo property to indicate which version of QB4OLAP is used in a dataset.

#Documentation and related resources
Our [wiki](https://github.com/lorenae/qb4olap/wiki) contains a detailed description of the elements of the vocabulary, examples of QB4OLAP in-use, and related publications.

Check our [QB4OLAP-tools](https://github.com/lorenae/qb4olap-tools) project to see some examples of what can be done with this vocabulary.

#Examples

The [examples](https://github.com/lorenae/qb4olap/tree/master/examples) folder contains ttl files that represent the schema and instances of different cubes in QB4OLAP

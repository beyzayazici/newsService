# News Service

### Introduction ###

  Filtering the news that received via an API according to the aho corasick algorithm and download excel file. Filtering is done according to the Ruleset.json file that is stored in project resources folder. Ruleset format must be json.
 
### Aho Corasick Algorithm References ###
   
   - [Aho Corasick Algorithm](https://iq.opengenus.org/aho-corasick-algorithm)
   - [Efficient String Matching: An Aid to Bibliographic Search](http://cr.yp.to/bib/1975/aho.pdf)
   
### Used Technologies ###

- Spring Boot
- Maven


### Output ###

- Excel

### Algorithm Usage ###

News are filtered with the Trie using a builder as follows: `   
    
    Trie trie = Trie.builder()
                .ignoreOverlaps() 
                .ignoreCase()
                .addKeywords("keyword to search in the news")
                .build();

        String text = "the part of the news search"

        Collection<Emit> emits = trie.parseText(text);
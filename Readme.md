Suggestions for improving the database:
1.) Create a OneToOne relationship between the film and film_text 
tables, because the film_text table has a film_id field that does 
not refer to the ID from the film table (no foreign key). 
But this connection must exist. In addition, in the film_text table,
the film_id field acts as the primary key, which ensures that no 
more than one “film text” corresponds to one “movie”.
2.) Convert all IDs in the database to one type, for example, 
Integer, and not as it is: Byte, Short, Integer. 
This will allow you to put IDs for all entities into a separate 
class.
3.) In the table "film" in a column "special_features" the
principle of database design is violated. Which makes sampling
for this column very difficult and inconvenient. You can make several
columns, ManyToMany relationship, where you can store these records.
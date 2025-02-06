<h1>Key Value Application</h1>

This application implements key value storage using Java, Spring boot, MongoDB, and Redis.
The supported operations are adding a key-value pair, extracting it, deleting, retrieving based on the key range, and
batch insertion.

<h2> Required software </h2>
1. Jdk >17 
2. Apache Maven >3.8.1
3. MongoDB >5
4. Redis >7.4
5. Docker 4.37.1
5. Editor. I used IntelliJ Community edition 2024.3.2.2

<h2> Installation and execution of the application </h2>
<h3>1. Clone the repository</h3>
git clone https://github.com/AbdulBasith88/moniepoint.git

<h3>2. Build the application </h3>
<p>mvn clean install</p>
This will compile, test, and packages the application.

<h3>3. Run the application </h3>
mvn spring-boot:run

<h2> API Endpoints </h2>
This is an optional. The base URL is http://localhost:8080/keyvalue

<h2> Troubleshoot </h2>
<h3>1. MongoDB Connection issues</h3>
Ensure the DB is running on the correct host and port. Update the property <b>spring.data.mongodb.uri</b> accordingly. 

<h3>2. Redis Connection issues </h3>
I have installed Redis on Docker locally. Follow the
article https://redis.io/docs/latest/operate/oss_and_stack/install/install-stack/docker/.

<h3>3. Lombok </h3>
IntelliJ may complain about getters and setters. Install the Lombok plugin.

<h2> Why MongoDB? </h2>
This is network based persistant and transactional based DB and supports all the required operations. 
The key features apart from storage and fast retrieval are fault tolerance, partitioning(Sharding), replication, automatic failover, and scalability are also supported.

I had a thought of using Cassandra which supports all these features but the retrieving using key range was challenging
when the range is across the partitioning. It is possible with partitioning key and clustering.

Comparison on a few required features

MongoDB<br>
Data Model: Document-based (JSON-like BSON)<br>
Fault Tolerance: Replica sets<br>
Partitioning: Sharding with shard keys<br>
Replication: Replica sets (Primary-Secondary)<br>
Range Queries: Supported on indexed fields<br>
Consistency: Tunable with read/write concerns<br>
Automatic Failover:    Built-in with replica sets<br>

Cassandra<br>
Data Model: Wide-column (Keyspace/Table/Row)<br>
Fault Tolerance: Replication within data centers<br>
Partitioning: Partitioning by primary key<br>
Replication: Consistent hashing<br>
Range Queries: Supported within partitions<br>
Consistency: Tunable consistency levels<br>
Automatic Failover: Built-in with replication<br>

<h3> MongoDB Sharding </h3>
MongoDB's sharding feature enables horizontal scaling by distributing data across multiple servers (shards). This helps manage datasets larger than RAM and ensures high performance.<br>
Each shard should be a replica set to provide fault tolerance.

To handle data larger than RAM, focus on optimizing access patterns to minimize disk I/O:

Efficient Indexing:

Ensure the key field is indexed. In MongoDB, the shard key is automatically indexed.
Add secondary indexes if range queries or specific filters are frequent.
Range Queries:

Utilize the \$gte and \$lte operators for range queries, which are efficient if the shard key or indexed field is used.
Batch Operations:

Use batch inserts and updates to reduce the number of disk writes.
MongoDB’s insertMany and bulk write APIs are ideal for this.


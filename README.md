# Developer Cell Phone Report
Sample for WCF

###Build
mvn clean compile assembly:single

###Run
java -jar target/developer_cell_phone-1.0-SNAPSHOT-jar-with-dependencies.jar

#####Params
 * year :expects a valid numeric 4 digit year
 * path :path to local file to write the data.
 * test :&lt;optional&gt; don't print if true - negative logic
#####Main method
 brad.chamberlain.report.Report
#####Test
 I didn't fully unit test, but some.
***
###Results
I was able to keep the original hope of keeping the data, busniness, and printing separated.  I did have to change my approach on the business logic a few times as I re-read the instructions.  I didn't like the way the math was working on doulbe/float values, so I simplified by converting them to ints and then back to double's for outputing the data.
 ##### Assumptions
 * The default printer is good enough.  No print dialog.
 * It's Ok to alter the output format a bit for readability.
 * Plain Text is Ok for final formatting.
 * It's Ok to write a local file to print.
 * We may not always want to print (though that was a requirement I didn't want to during test runs)
 * You won't get mad if I spent more time than you intended.
 #####Package structure
 I tried to keep the data/report/print logic separated.
 * Package dataload handles the loading of the database.  I was imagining a scenario where we might want to change this to a database.
 * Package report handles the logic for building the report.  You'll see that I handled the doulbe values a bit funny.  I converted them to int's then back to doubles so I didn't have to deal with wonky double math.  
 * Package print handles printing.  I've never implemented local printing so I just found an example online and copied that.  
 **  I thought about generating a PDF from the html formatter, but that felt overkill.
 * Package format was my first and second attempts to send to printing.  I ended up abstracting this a bit because my local printer doesn't support direct html printing so I changed to text/plain.
***
##Pre-work thoughts
  This is what was on my mind when I started.
####Problem definition:

* Read the data in from the provided CSV files.
* Aggregate Summary data by year.
* Aggregate the data into employee, year, month data.
* Print to local printer.

####Assumptions
* Report Date will be the local date of the system running the report.  
* All dates input in the report are report local time.
* Kind of stated, but I'm assuming year should be provided.
* Output format, haven't decided how I want to do this yet.
* Avergae minutes and data, by phone or per usage.  I'll do per usage and toss out 0 values
* Time and JUnit tests, I'll do my best.
* Input data is somewhat clean, I hope.
* It seems weird that the report header is totals, not averages per month.  I'm not going to summarize monthly.

####Abstractions
Nice to do, but I don't know if I will
1.  Data load abstractions, would allow changing input formats/sources.
2.  Report field customizations.  I won't do this.
3.  Output is ideally configurable, for first pass I'm just sending to default.

***

I was thinking I could load the data into a sql lite database and do all the work there.  That feels like overkill.  I'm going to keep it simple and model the report output and build collections that are keyed off the reports.  Hopefully the java collections interface can do most of this.  I'm probably not going to test too much the data load and printer functions, but I should be able to tdd the report builders.

***

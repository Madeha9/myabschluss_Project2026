# Stakeholders and Context Analysis

## Stakeholders
| Name                    | Role                     | Interest                                                                                                                                                     |
|-------------------------|--------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------|
| End Users (Customers)   | Users of the application | Store and access invoices digitally, retrieve invoices when returning products , and receive decision support during shopping to avoid unnecessary purchases |
| Project Owner (Student) | Developer & designer     | Successfully complete the final project and demonstrate software development skills                                                                          |
| Trainers / Evaluators   | Reviewers                | Assess project quality, architecture, and documentation                                                                                                      |
| Retailers (Indirect)    | Invoice issuers          | Provide invoices that can be scanned and stored digitally                                                                                                    |


## Business Context
The Smart Invoice project exists to solve a common real-world problem:
customers lose paper invoices, which prevents them from returning or exchanging products.

Invoices are often:

* Lost within days
* Damaged or unreadable
* Deleted when QR codes or numbers fade

As a result, customers are forced to:

* Keep unwanted products
* Waste money
* Miss return deadlines
* Forget store return policies (e.g. refund vs store credit, 14 vs 30 days)

The system solves this problem by:

* Digitally storing invoice QR codes and invoice data
* Saving return policies per store
* Sending notifications before the return deadline
* Helping users make smarter shopping decisions
* Using an AI agent to analyze shopping behavior and reduce unnecessary purchases

The system can be used by individuals during shopping  and potentially by stores as a digital invoice solution.

The system is developed as a student project within an academic environment and is evaluated
as part of a software development course. Despite its academic scope, 
the project addresses a real-world retail problem faced by customers in physical stores.
The system operates in physical retail environments with internet access and must handle 
varying return policies across different stores. Its goal is to support customers by digitally storing invoice information,
tracking return deadlines, and improving decision-making during shopping.
## Technical Context

The project is implemented as a final project for the Software Development course
at WIFI and was developed over a period of approximately two months. The system is designed to 
demonstrate practical software engineering skills within an academic scope.

**Technologies and Tools**

* Programming Language: Java
* Integrated Development Environment (IDE): IntelliJ IDEA
* Version Control and project management: Git and GitHub
* Database: PostgreSQL (SQL)
* Artificial Intelligence: LLM libraries used to implement AI agent functionality
* Deployment: Cloud-based environment to allow user access

**Constraints**

* The project is developed by a single developer
* Development time is limited due to academic deadlines
* No access to large-scale, real-world retail datasets
* No access to banking information or online payment systems
* The system is intended for clothing and footwear products purchased in physical stores and does not cover food or grocery items
* AI-related features are implemented at a prototype level and are not production-ready

## Risks and Assumptions

**Risks**

* Invoice images may be unclear, making QR code scanning or data extraction impossible
* Uploaded invoices may not contain structured or readable information
* AI recommendations may be limited due to lack of real user data
* Dependence on external libraries could cause technical limitations
* Cloud availability or performance issues may affect user access

**Assumptions**

* Users upload readable invoice images or QR codes
* Stores accept digital invoices for returns or exchanges
* Users follow store return policies saved in the system
* Internet access is available to retrieve stored invoices
* The system is used for personal invoice management, not legal or tax archiving

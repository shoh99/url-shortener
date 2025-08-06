# URL Shortener API - Spring Boot

This is a robust and scalable URL shortening service built with Spring Boot. It allows users to convert long URLs into short, manageable links, create custom vanity URLs, and track click analytics.

---

## ✨ Features

* **URL Shortening**: Converts long URLs into unique, short codes using a Base62 encoding algorithm.
* **Custom Short URLs**: Users can provide their own custom alias (vanity URL) for their links.
* **Click Analytics**: Tracks every click on a shortened link, capturing IP address, User-Agent, and referrer information.
* **Asynchronous Processing**: Click analytics are logged asynchronously to avoid slowing down the user redirect.
* **RESTful API**: A clean and simple API for creating and managing URLs.

---

## 🛠️ Technology Stack

* **Framework**: Spring Boot 3
* **Language**: Java 17
* **Database**: PostgreSQL
* **Build Tool**: Maven
* **Project Management**: Lombok
* **Deployment**: Docker

---

## 🚀 Getting Started

Follow these instructions to get the project up and running on your local machine.

### Prerequisites

* **JDK 17** or later
* **Maven 3.8** or later
* **Docker** and **Docker Compose**

### 1. Clone the Repository

```bash
git clone <your-repository-url>
cd url-shortener
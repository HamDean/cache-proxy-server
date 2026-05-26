# Cache Proxy Server

A lightweight **HTTP caching proxy server** built with **Spring Boot** that forwards client requests to an origin server and caches responses to improve performance and reduce redundant network calls.

*NB:* This project is a personal project for learning and experimentation, not production-ready.
## Features

- 🚀 **Request Forwarding** – Proxies incoming HTTP requests to a configured origin server
- 💾 **Response Caching** – Stores responses in memory for faster repeated access
- ⚡ **Cache Hit Detection** – Quickly serves cached responses when available
- 🗑️ **Cache Invalidation** – Clear all cached responses with a dedicated endpoint
- 🔧 **Configurable Origin Server** – Set the upstream server via environment variables
- 📦 **Standalone Executable** – Packaged as a runnable JAR with a launcher script

---

## Tech Stack

- **Java 25**
- **Spring Boot**
- **Maven**
- **In-memory Cache (ConcurrentHashMap / custom cache store)**

---

## Project Structure

```bash
cache-proxy-server/
├── src/
│   ├── main/
│   │   ├── java/                  # Application source code
│   │   └── resources/
│   │       └── application.yml    # App configuration
├── target/
│   ├── caching-proxy-server-0.0.1-SNAPSHOT.jar   
├── pom.xml
|--- cache-proxy-server.bat   # Windows launcher script
└── README.md
```

---

## Prerequisites

Before running the project, make sure you have:

- **Java 25+**
- **Git**
- *(Optional)* **Maven**  
  You can use the included Maven Wrapper, so Maven installation is not required.

---

## Installation

### 1. Clone the repository

```bash
git clone https://github.com/HamDean/cache-proxy-server.git
cd cache-proxy-server
```

---

### 2. Build the project

Using Maven Wrapper:

```bash
./mvnw clean package
```

On Windows:

```bash
mvnw.cmd clean package
```

This generates the executable JAR inside:

```bash
target/
```

---

## Running the Server

### Option 1: Run using Java

```bash
java -jar target/caching-proxy-server-0.0.1-SNAPSHOT.jar --origin=https://example.com --port=8080
```

---

### Option 2: Run using the batch script (Windows)

From the `target/` directory:

```bash
run.bat --origin=https://example.com --port=8080
```

---

## Configuration

The application accepts the following parameters:

| Parameter | Description | Example |
|-----------|-------------|---------|
| `origin` | Target server to proxy requests to | `https://dummyjson.com` |
| `port` | Port for the proxy server | `8080` |

### Example `application.yml`

```yaml
spring:
  application:
    name: caching-proxy-server

server:
  port: ${port}

proxy:
  origin: ${origin}
```

---

## How It Works

1. Client sends a request to the proxy server.
2. The proxy checks whether the response exists in cache.
3. If **cache hit**:
    - Return cached response immediately.
4. If **cache miss**:
    - Forward request to the origin server.
    - Store the response in cache.
    - Return the fresh response.

---

## Cache Management

### Clear the Cache

Use the cache invalidation endpoint:

```http
DELETE /cache
```

This removes all stored cached responses.

---

## Example Usage

Start proxy:

```bash
java -jar target/caching-proxy-server-0.0.1-SNAPSHOT.jar --origin=https://dummyjson.com --port=3000
```

Then request through proxy:

```bash
curl http://localhost:3000/products
```

First request:

```text
Cache MISS → Fetching from origin
```

Subsequent request:

```text
Cache HIT → Returning cached response
```

---

## Development

Run locally without packaging:

```bash
./mvnw spring-boot:run
```

---

## Future Improvements

- TTL-based cache expiration
- Selective cache invalidation by endpoint
- Persistent disk-based caching
- Cache statistics endpoint
- Support for cache headers (`ETag`, `Cache-Control`)
- Logging and monitoring

---


## Author

**Nurudeen Hamzah**

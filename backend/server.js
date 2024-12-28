const express = require("express");
const colors = require("colors");
const dotenv = require("dotenv").config();
const { errorHandler } = require("./middleware/errorTempMiddleware.js");

const connectDB = require("./config/db");
const port = process.env.PORT || 5000;

// Connect to the database
connectDB();

const app = express();

// Middleware for parsing JSON and form data
app.use(express.json());
app.use(express.urlencoded({ extended: false }));

// Auth routes
app.use("/api/auth", require("./routes/authRoutes"));

// Book routes
app.use("/api/books", require("./routes/bookRoutes"));

// Error handler middleware
app.use(errorHandler);

app.listen(port, () => console.log(`Server started on port ${port}`));

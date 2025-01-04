const express = require("express");
const { filterBooksByCategory } = require("../controllers/filterController");
const router = express.Router();

// Route to filter books by category
router.get("/", filterBooksByCategory);

module.exports = router;

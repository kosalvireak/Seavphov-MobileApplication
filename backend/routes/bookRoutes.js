const express = require("express");
const {
  getBooks,
  getBookById,
  createBook,
  updateBook,
  deleteBook,
} = require("../controllers/bookController");

const router = express.Router();

router.route("/").get(getBooks).post(createBook);
router.route("/:id").get(getBookById).put(updateBook).delete(deleteBook);

module.exports = router;

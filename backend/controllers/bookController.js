const asyncHandler = require("express-async-handler");
const Book = require("../model/bookModel");

// Get all books
const getBooks = asyncHandler(async (req, res) => {
  const books = await Book.find();
  res.status(200).json({ status: "success", data: books });
});

// Get a single book by ID
const getBookById = asyncHandler(async (req, res) => {
  const book = await Book.findById(req.params.id);
  if (!book) {
    res.status(404);
    throw new Error("Book not found");
  }
  res.status(200).json({ status: "success", data: book });
});

// Create a new book
const createBook = asyncHandler(async (req, res) => {
  const { title, description, author, category, condition, location, imgUrl } =
    req.body;

  if (
    !title ||
    !description ||
    !author ||
    !category ||
    !condition ||
    !location ||
    !imgUrl
  ) {
    res.status(400);
    throw new Error("Please provide all required fields");
  }

  const book = await Book.create({
    title,
    description,
    author,
    category,
    condition,
    location,
    imgUrl,
  });

  res.status(201).json({ status: "success", data: book });
});

// Update a book by ID (field-specific updates supported)
const updateBook = asyncHandler(async (req, res) => {
  const book = await Book.findById(req.params.id);

  if (!book) {
    res.status(404);
    throw new Error("Book not found");
  }

  // Update specific fields if they are provided in the request body
  const fieldsToUpdate = req.body;

  Object.keys(fieldsToUpdate).forEach((field) => {
    book[field] = fieldsToUpdate[field];
  });

  const updatedBook = await book.save();

  res.status(200).json({ status: "success", data: updatedBook });
});

// Delete a specific field of a book by ID
const deleteBookField = asyncHandler(async (req, res) => {
  const { id, field } = req.params;

  const book = await Book.findById(id);

  if (!book) {
    res.status(404);
    throw new Error("Book not found");
  }

  if (!book[field]) {
    res.status(400);
    throw new Error(`Field "${field}" does not exist in the book model`);
  }

  // Remove the field from the book object
  delete book[field];

  await book.save();

  res.status(200).json({
    status: "success",
    message: `Field "${field}" deleted`,
    data: book,
  });
});

// Delete a book by ID (updated with findByIdAndDelete)
const deleteBook = asyncHandler(async (req, res) => {
  const book = await Book.findById(req.params.id);

  if (!book) {
    res.status(404);
    throw new Error("Book not found");
  }

  // Use findByIdAndDelete instead of remove
  await Book.findByIdAndDelete(req.params.id);

  res.status(200).json({ status: "success", message: "Book deleted" });
});

module.exports = {
  getBooks,
  getBookById,
  createBook,
  updateBook,
  deleteBookField,
  deleteBook,
};

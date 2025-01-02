const asyncHandler = require("express-async-handler");
const Book = require("../model/bookModel");
const User = require("../model/userModel");
const jwt = require("jsonwebtoken");

// Utility function to get the authenticated user from the token
const getUserFromToken = async (token) => {
  try {
    const decoded = jwt.verify(token, process.env.JWT_SECRET);
    const user = await User.findById(decoded.id);
    return user;
  } catch (error) {
    throw new Error("Not authorized, token failed");
  }
};

// Get all books
const getBooks = asyncHandler(async (req, res) => {
  try {
    const books = await Book.find().populate("ownerId", "name imgUrl");

    res.status(200).json({
      status: "success",
      message: "Books retrieved successfully",
      data: books,
    });
  } catch (error) {
    res.status(500).json({
      status: "failed",
      message: "Error retrieving books",
      error: error.message,
    });
  }
});

// Get a single book by ID
const getBookById = asyncHandler(async (req, res) => {
  const book = await Book.findById(req.params.id).populate(
    "ownerId",
    "name imgUrl"
  );

  if (!book) {
    res.status(404).json({
      status: "failed",
      message: "Book not found",
    });
    return;
  }

  res.status(200).json({
    status: "success",
    message: "Book retrieved successfully",
    data: book,
  });
});

// Create a new book
const createBook = asyncHandler(async (req, res) => {
  const {
    title,
    description,
    author,
    category,
    condition,
    location,
    price,
    imgUrl,
  } = req.body;

  if (
    !title ||
    !description ||
    !author ||
    !category ||
    !condition ||
    !location ||
    !imgUrl ||
    !price
  ) {
    res.status(400).json({
      status: "failed",
      message: "Please provide all required fields",
    });
    return;
  }

  const token = req.headers.authorization;

  if (!token) {
    res.status(401).json({
      status: "failed",
      message: "No token, authorization denied",
    });
    return;
  }

  const user = await getUserFromToken(token);

  const newBook = await Book.create({
    title,
    description,
    author,
    category,
    condition,
    location,
    price,
    imgUrl,
    ownerId: user._id,
  });

  res.status(201).json({
    status: "success",
    message: "Book created successfully",
    data: newBook,
  });
});

// Update a book by ID
const updateBook = asyncHandler(async (req, res) => {
  const book = await Book.findById(req.params.id);

  if (!book) {
    res.status(404).json({
      status: "failed",
      message: "Book not found",
    });
    return;
  }

  const token =
    req.headers.authorization && req.headers.authorization.split(" ")[1];
  if (!token) {
    res.status(401).json({
      status: "failed",
      message: "No token, authorization denied",
    });
    return;
  }

  const user = await getUserFromToken(token);

  if (book.ownerId.toString() !== user._id.toString()) {
    res.status(403).json({
      status: "failed",
      message: "Not authorized to update this book",
    });
    return;
  }

  Object.assign(book, req.body);
  const updatedBook = await book.save();

  res.status(200).json({
    status: "success",
    message: "Book updated successfully",
    data: updatedBook,
  });
});

// Delete a book by ID
const deleteBook = asyncHandler(async (req, res) => {
  const book = await Book.findById(req.params.id);

  if (!book) {
    res.status(404).json({
      status: "failed",
      message: "Book not found",
    });
    return;
  }

  const token =
    req.headers.authorization && req.headers.authorization.split(" ")[1];
  if (!token) {
    res.status(401).json({
      status: "failed",
      message: "No token, authorization denied",
    });
    return;
  }

  const user = await getUserFromToken(token);

  if (book.ownerId.toString() !== user._id.toString()) {
    res.status(403).json({
      status: "failed",
      message: "Not authorized to delete this book",
    });
    return;
  }

  await book.remove();

  res.status(200).json({
    status: "success",
    message: "Book deleted successfully",
  });
});

module.exports = {
  getBooks,
  getBookById,
  createBook,
  updateBook,
  deleteBook,
};

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
  const books = await Book.find().populate("owner", "name imgUrl");
  res.status(200).json({ status: "success", data: books });
});

// Get a single book by ID
const getBookById = asyncHandler(async (req, res) => {
  let book = await Book.findById(req.params.id);

  if (!book) {
    res.status(404);
    throw new Error("Book not found");
  }

  const owner = await User.findById(book.ownerId);

  const ownerInfo = {
    name: owner.name,
    imgUrl: owner.imgUrl,
  };

  const bookWithOwnerDetails = {
    ...book.toObject(), // Convert Mongoose document to plain object
    owner: ownerInfo,
  };

  res.status(200).json({ status: "success", data: bookWithOwnerDetails });
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

  // Validate required fields
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
    res.status(400);
    throw new Error("Please provide all required fields");
  }

  console.log("req", req.headers);
  // Verify and get the authenticated user from the token
  const token = req.headers.authorization;

  if (!token) {
    res.status(401);
    throw new Error("No token, authorization denied");
  }

  const user = await getUserFromToken(token);

  // Create the new book and associate it with the authenticated user
  const newBook = await Book.create({
    title,
    description,
    author,
    category,
    condition,
    location,
    price,
    imgUrl,
    ownerId: user._id, // Set the book ownerId to the authenticated user
  });

  res.status(201).json({
    status: "success",
    data: newBook,
    message: "Book created successfully",
  });
});

// Update a book by ID
const updateBook = asyncHandler(async (req, res) => {
  const book = await Book.findById(req.params.id);

  if (!book) {
    res.status(404);
    throw new Error("Book not found");
  }

  // Verify and get the authenticated user from the token
  const token =
    req.headers.authorization && req.headers.authorization.split(" ")[1]; // Get token from headers
  if (!token) {
    res.status(401);
    throw new Error("No token, authorization denied");
  }

  const user = await getUserFromToken(token);

  // Check if the authenticated user is the owner of the book
  if (book.owner.toString() !== user._id.toString()) {
    res.status(403);
    throw new Error("Not authorized to update this book");
  }

  // Update the book fields
  const fieldsToUpdate = req.body;
  Object.keys(fieldsToUpdate).forEach((field) => {
    book[field] = fieldsToUpdate[field];
  });

  const updatedBook = await book.save();
  res.status(200).json({ status: "success", data: updatedBook });
});

// Delete a book by ID
const deleteBook = asyncHandler(async (req, res) => {
  const book = await Book.findById(req.params.id);

  if (!book) {
    res.status(404);
    throw new Error("Book not found");
  }

  // Verify and get the authenticated user from the token
  const token =
    req.headers.authorization && req.headers.authorization.split(" ")[1]; // Get token from headers
  if (!token) {
    res.status(401);
    throw new Error("No token, authorization denied");
  }

  const user = await getUserFromToken(token);

  // Check if the authenticated user is the owner of the book
  if (book.owner.toString() !== user._id.toString()) {
    res.status(403);
    throw new Error("Not authorized to delete this book");
  }

  await book.remove();
  res
    .status(200)
    .json({ status: "success", message: "Book deleted successfully" });
});

// Get books posted by a specific owner
const getBooksByOwner = asyncHandler(async (req, res) => {
  const books = await Book.find({ owner: req.params.ownerId }).populate(
    "owner",
    "name imgUrl"
  );

  if (!books.length) {
    res.status(404);
    throw new Error("No books found for this owner");
  }

  res.status(200).json({ status: "success", data: books });
});

module.exports = {
  getBooks,
  getBookById,
  createBook,
  updateBook,
  deleteBook,
  getBooksByOwner,
};

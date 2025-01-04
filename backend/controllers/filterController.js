const Book = require("../model/bookModel");

// Allowed categories
const allowedCategories = ["Fantasy", "History", "Horror", "Romance"];

// Filter books by category
const filterBooksByCategory = async (req, res) => {
  try {
    console.log("filterBooksByCategory", req.query);

    const { category } = req.query; // Extract the category from the query parameter

    // Check if the category is valid
    if (!category || !allowedCategories.includes(category)) {
      return res.status(400).json({
        message: `Invalid category. Allowed categories are: ${allowedCategories.join(
          ", "
        )}`,
      });
    }

    console.log("Filtering books with category:", category); // Debugging line

    // Query books with the category field
    const books = await Book.find({ category });

    if (books.length === 0) {
      return res
        .status(404)
        .json({ message: `No books found for category ${category}` });
    }

    res.status(200).json(books); // Return the filtered books
  } catch (err) {
    console.error("Error:", err); // Log error for debugging
    res.status(500).json({ message: err.message }); // Send error response
  }
};

module.exports = { filterBooksByCategory };

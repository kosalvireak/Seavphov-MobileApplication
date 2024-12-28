const mongoose = require("mongoose");

const bookSchema = mongoose.Schema(
  {
    title: {
      type: String,
      required: [true, "Please add a title"],
    },
    description: {
      type: String,
      required: [true, "Please add a description"],
    },
    author: {
      type: String,
      required: [true, "Please add an author"],
    },
    category: {
      type: String,
      required: [true, "Please add a category"],
    },
    condition: {
      type: String,
      required: [true, "Please add the condition"],
    },
    location: {
      type: String,
      required: [true, "Please add the location"],
    },
    imgUrl: {
      type: String,
      required: [true, "Please add an image URL"],
    },
  },
  {
    timestamps: true,
  }
);

module.exports = mongoose.model("Book", bookSchema);

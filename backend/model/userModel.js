const mongoose = require("mongoose");

const userSchema = new mongoose.Schema(
  {
    gmail: {
      type: String,
      required: true,
      unique: true,
    },
    password: {
      type: String,
      required: true,
    },
    name: {
      type: String,
      required: true, // Name is now a required field
    },
    imgUrl: {
      type: String,
      required: false, // Image URL is optional
    },
    token: {
      type: String,
      required: false, // Token is optional and can be added when generated
    },
  },
  { timestamps: true }
);

module.exports = mongoose.model("User", userSchema);

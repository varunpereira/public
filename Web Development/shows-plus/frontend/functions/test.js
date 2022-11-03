const expressrouter = require("express");
const app = express();
const serverless = require("serverless-http");

app.use("/api/user");

app.post("/test", (req, res) => {
  return "hi";
});

module.exports.handler = serverless(app);

// exports.handler = async (event, context) => {
//   return {
//     statusCode: 200,
//     body: JSON.stringify({ msg: event.httpMethod }),
//   };
// };

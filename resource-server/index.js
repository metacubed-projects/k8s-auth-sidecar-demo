const http = require('http');
const qs = require('querystring');
const url = require('url');

let data = {
    'default': 'default-value'
};

const port = process.env.PORT || 80
const server = http.createServer((req,res) => {

    const {pathname, query} = url.parse(req.url);
    if (pathname === '/data') {
        const {key,value} = qs.parse(query);
        if (req.method === 'GET') {
            res.writeHead(200, {
                'Content-Type': 'application/json',
             });
            res.statusCode = 200;
            res.end('{ "' + key + '": "' + data[key] + '" }');
        } else if (req.method === 'POST') {
            data[key] = value;
            res.writeHead(201, {
                'Content-Type': 'application/json',
            });
            res.statusCode = 201;
            res.end('{ "' + key + '": "' + data[key] + '" }');
        } else {
            handleError(res, 405);
        }
    } else {
        handleError(res, 404);
    }
});

function handleError(res, code) {
    res.statusCode = code;
    res.end(`{ "error": "${http.STATUS_CODES[code]}" }`);
}

server.listen(port, () => {
    console.log(`Server listening on the port ${port}`);
})

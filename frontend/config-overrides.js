const path = require('path');

module.exports = function override(config, env) {
    config.resolve.extensions.push('.jsx');
    return config;
};

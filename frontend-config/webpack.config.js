'use strict'

const path = require('path');
const fs = require('fs')
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const CopyWebpackPlugin = require("copy-webpack-plugin");

const generateHtmlPlugins = (dir) => {
    const templateFiles = fs.readdirSync(dir);
    return templateFiles.map(item => {
        return new HtmlWebpackPlugin({
            template: path.join(__dirname, `src/html/${item}`),
            filename: path.resolve(__dirname, `../src/main/resources/static/html/${item}`)
        })
    });
};

const htmlDir = path.resolve(__dirname, 'src/html');
const htmlPlugins = generateHtmlPlugins(htmlDir);


module.exports = {
    mode: 'production',
    entry: path.resolve(__dirname, 'src/js/index.js'),
    output: {
        filename: '[name].bundle.js',
        path: path.resolve(__dirname, '../src/main/resources/static/js/dist'),
        clean: true
    },
    optimization: {
        splitChunks: {
            chunks: 'all'
        }
    },
    devtool: 'source-map',
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /node_modules/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env']
                    }
                }
            },
            {
                test: /\.scss$/,
                use: [
                    MiniCssExtractPlugin.loader,
                    'css-loader',
                    'sass-loader'
                ]
            },
            {
                test: /\.css$/,
                use: [MiniCssExtractPlugin.loader, 'css-loader']
            },
        ]
    },
    plugins: [
        new MiniCssExtractPlugin({
            filename: '../../css/[name].bundle.css'
        }),
        new HtmlWebpackPlugin({
            template: path.resolve(__dirname, 'src/index.html'),
            filename: path.resolve(__dirname, '../src/main/resources/static/index.html')
        }),
        ...htmlPlugins,
        new CopyWebpackPlugin({
            patterns: [
                {
                    from: path.resolve(__dirname, 'src/assets/icons'),
                    to: path.resolve(__dirname, '../src/main/resources/static/assets/icons')
                },
                {
                    from: path.resolve(__dirname, 'src/assets/images'),
                    to: path.resolve(__dirname, '../src/main/resources/static/assets/images')
                }
            ]
        })
    ],
    resolve: {
        modules: [
            path.resolve(__dirname, 'node_modules'),
            path.resolve(__dirname, 'src/js')
        ],
        extensions: ['.js', '.scss']
    },
    devServer: {
        static: path.resolve(__dirname,'../src/main/resources/static'),
        port: 8081
    },
};
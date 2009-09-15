//from prototype library

String.prototype.stripTags = function()
{
    return this.replace(/<\/?[^>]+>/gi, '');
};

String.prototype.stripScripts = function()
{
    return this.replace(new RegExp('<script[^>]*>([\\S\\s]*?)<\/script>', 'img'), '');
};

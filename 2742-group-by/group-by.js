/**
 * @param {Function} fn
 * @return {Object}
 */
Array.prototype.groupBy = function(fn) {
    // 1. Initialize the object that will hold the groups
    const grouped = {};
    
    // 2. Iterate over the array. 
    // In a prototype method, 'this' refers to the array instance itself.
    for (const item of this) {
        // 3. Generate the key using the callback function
        const key = fn(item);
        
        // 4. If the key doesn't exist yet, initialize it with an empty array
        if (!grouped[key]) {
            grouped[key] = [];
        }
        
        // 5. Push the current item into the correct group
        grouped[key].push(item);
    }
    
    // 6. Return the grouped object
    return grouped;
};

/**
 * [1,2,3].groupBy(String) // {"1":[1],"2":[2],"3":[3]}
 */
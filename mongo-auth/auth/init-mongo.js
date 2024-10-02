db.createUser({
    user: 'admin',
    pwd: 'pass',
    roles: [
        {
            role: 'readWrite',
            db: 'users_db'
        }
    ]
});
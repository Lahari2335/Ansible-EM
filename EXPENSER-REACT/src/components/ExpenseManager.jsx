import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './style.css';

const ExpenseManager = () => {
  const [expenses, setExpenses] = useState([]);
  const [expense, setExpense] = useState({ id: '', title: '', amount: '' });
  const [idToFetch, setIdToFetch] = useState('');
  const [fetchedExpense, setFetchedExpense] = useState(null);
  const [message, setMessage] = useState('');
  const [editMode, setEditMode] = useState(false);

  const baseUrl = import.meta.env.VITE_API_URL || 'http://localhost:2000/expenseapi';

  useEffect(() => {
    fetchAllExpenses();
  }, []);

  const fetchAllExpenses = async () => {
    try {
      const res = await axios.get(`${baseUrl}/all`);
      setExpenses(res.data);
    } catch {
      setMessage('Failed to fetch expenses.');
    }
  };

  const handleChange = (e) => setExpense({ ...expense, [e.target.name]: e.target.value });

  const addExpense = async () => {
    try {
      await axios.post(`${baseUrl}/add`, expense);
      fetchAllExpenses();
      setExpense({ id: '', title: '', amount: '' });
    } catch {
      setMessage('Failed to add expense');
    }
  };

  const updateExpense = async () => {
    try {
      await axios.put(`${baseUrl}/update/${expense.id}`, expense);
      fetchAllExpenses();
      setEditMode(false);
      setExpense({ id: '', title: '', amount: '' });
    } catch {
      setMessage('Failed to update');
    }
  };

  const deleteExpense = async (id) => {
    try {
      await axios.delete(`${baseUrl}/delete/${id}`);
      fetchAllExpenses();
    } catch {
      setMessage('Failed to delete');
    }
  };

  const getExpenseById = async () => {
    try {
      const res = await axios.get(`${baseUrl}/get/${idToFetch}`);
      setFetchedExpense(res.data);
    } catch {
      setFetchedExpense(null);
      setMessage('Not found');
    }
  };

  const handleEdit = (exp) => {
    setExpense(exp);
    setEditMode(true);
  };

  return (
    <div>
      <h2>Expense Manager</h2>

      <div>
        <h3>{editMode ? 'Edit Expense' : 'Add Expense'}</h3>
        <input name="id" placeholder="ID" value={expense.id} onChange={handleChange} />
        <input name="title" placeholder="Title" value={expense.title} onChange={handleChange} />
        <input name="amount" placeholder="Amount" value={expense.amount} onChange={handleChange} />
        {!editMode ? (
          <button onClick={addExpense}>Add</button>
        ) : (
          <button onClick={updateExpense}>Update</button>
        )}
      </div>

      <div>
        <h3>Get Expense By ID</h3>
        <input value={idToFetch} onChange={(e) => setIdToFetch(e.target.value)} placeholder="ID" />
        <button onClick={getExpenseById}>Fetch</button>
        {fetchedExpense && <pre>{JSON.stringify(fetchedExpense, null, 2)}</pre>}
      </div>

      <div>
        <h3>All Expenses</h3>
        {expenses.map((exp) => (
          <div key={exp.id}>
            {exp.id} - {exp.title} - ₹{exp.amount}
            <button onClick={() => handleEdit(exp)}>Edit</button>
            <button onClick={() => deleteExpense(exp.id)}>Delete</button>
          </div>
        ))}
      </div>

      {message && <p>{message}</p>}
    </div>
  );
};

export default ExpenseManager;
